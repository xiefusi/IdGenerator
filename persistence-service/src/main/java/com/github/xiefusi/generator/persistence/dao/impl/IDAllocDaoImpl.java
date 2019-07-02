package com.github.xiefusi.generator.persistence.dao.impl;

import com.github.xiefusi.generator.persistence.dao.IDAllocDao;
import com.github.xiefusi.generator.persistence.properties.KeyGeneratorProperties;
import com.zaxxer.hikari.util.DriverDataSource;
import com.github.xiefusi.generator.persistence.model.GeneratorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * dao交互
 */
@Component
public class IDAllocDaoImpl implements IDAllocDao {

    private final String initTableSql = "\n"
            + "CREATE TABLE IF NOT EXISTS `t_key_generator` (\n"
            + "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',\n"
            + "  `business_id` varchar(128) NOT NULL DEFAULT '' COMMENT '业务id',\n"
            + "  `max_id` bigint(20) NOT NULL DEFAULT '1' COMMENT '最大id',\n"
            + "  `step` int(11) NOT NULL COMMENT '步长',\n"
            + "  `description` varchar(255) DEFAULT NULL COMMENT '描述',\n"
            + "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
            + "  PRIMARY KEY (`id`),\n"
            + "  UNIQUE KEY `uk_business_id` (`business_id`)\n"
            + ") ENGINE=InnoDB  COMMENT='分布式自增主键' ;";
    private final String existBusinessSql = "SELECT count(1) AS business_count FROM t_key_generator WHERE business_id = ? ";
    private final String insertBusinessSql = "INSERT INTO t_key_generator (business_id,max_id,step,description,update_time) VALUES (?,?,?,?,now())";
    private final String queryAllBusinessSql = "SELECT id,business_id,max_id,step,description,update_time FROM t_key_generator";
    private final String queryBusinessByBusinessId = "SELECT id,business_id,max_id,step,description,update_time FROM t_key_generator WHERE business_id = ?";
    private final String updateMaxIdByCustomStepSql = "UPDATE t_key_generator SET max_id = max_id + ? WHERE business_id = ?";
    private final String updateMaxIdSql = "UPDATE t_key_generator SET max_id = max_id + step WHERE business_id = ?";
    private final String queryAllBusinessIdSql = "SELECT business_id FROM t_key_generator";


    private DataSource dataSource;

    public IDAllocDaoImpl(@Autowired(required = false) DataSource dataSource, @Autowired KeyGeneratorProperties properties) throws SQLException {
        if (properties.isDbcust()) {
            KeyGeneratorProperties.DBProperties datasourceProperties = properties.getDatasource();
            if (Objects.isNull(dataSource)) {
                throw new RuntimeException("missing datasource config");
            }
            this.dataSource = new DriverDataSource(
                    datasourceProperties.getUrl(),
                    datasourceProperties.getDriverClassName(),
                    new Properties(),
                    datasourceProperties.getUsername(),
                    datasourceProperties.getPassword()
            );
//            DruidDataSource dbSource = new DruidDataSource();
//            dbSource.setUrl(datasource.getUrl());
//            dbSource.setUsername(datasource.getUsername());
//            dbSource.setPassword(datasource.getPassword());
//            dbSource.init();
//            this.dataSource = dbSource;
        } else {
            if (Objects.isNull(dataSource)) {
                throw new RuntimeException("datasource is required");
            }
            this.dataSource = dataSource;
        }
    }

    @Override
    public boolean initBusinessRecord(KeyGeneratorProperties.Business business) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(existBusinessSql);
            ps.setString(1, business.getBusinessId());
            rs = ps.executeQuery();
            if (!(rs.next() && Integer.valueOf(1).equals(rs.getInt("business_count")))) {
                close(rs);
                close(ps);
                ps = connection.prepareStatement(insertBusinessSql);
                ps.setString(1, business.getBusinessId());
                ps.setLong(2, business.getBegin());
                ps.setInt(3, business.getStep());
                ps.setString(4, business.getDescription());
                ps.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return false;
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return true;
    }

    @Override
    public List<GeneratorModel> getAllGeneratorModel() {
        List<GeneratorModel> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement(queryAllBusinessSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(tranToGeneratorModel(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return result;

    }

    private GeneratorModel tranToGeneratorModel(ResultSet rs) throws SQLException {
        GeneratorModel model = new GeneratorModel();
        if (rs.next()) {
            model.setId(rs.getLong("id"));
            model.setBusinessId(rs.getString("business_id"));
            model.setMaxId(rs.getLong("max_id"));
            model.setStep(rs.getInt("step"));
            model.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
        }
        return model;
    }

    @Override
    public GeneratorModel updateMaxIdAndGetGeneratorModel(String tag) {
        GeneratorModel model = new GeneratorModel();
        model.setBusinessId(tag);
        model.setStep(-1);
        return updateMaxIdByCustomStepAndGetGeneratorModel(model);
    }

    @Override
    public GeneratorModel updateMaxIdByCustomStepAndGetGeneratorModel(GeneratorModel model) {
        GeneratorModel result = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            if (model.getStep() < 0) {
                ps = connection.prepareStatement(updateMaxIdSql);
                ps.setString(1, model.getBusinessId());

            } else {
                ps = connection.prepareStatement(updateMaxIdByCustomStepSql);
                ps.setInt(1, model.getStep());
                ps.setString(2, model.getBusinessId());
            }
            ps.executeUpdate();
            close(ps);
            ps = connection.prepareStatement(queryBusinessByBusinessId);
            ps.setString(1, model.getBusinessId());
            rs = ps.executeQuery();
            result = tranToGeneratorModel(rs);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return result;

    }

    @Override
    public List<String> getAllBusinessId() {
        List<String> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement(queryAllBusinessIdSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("business_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean initTable() {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement(initTableSql);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(ps);
            close(connection);
        }
        return true;
    }

    private void close(AutoCloseable ac) {
        if (Objects.nonNull(ac)) {
            try {
                ac.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
