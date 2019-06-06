package io.xiefs.utils.generator.persistence.service;

import io.xiefs.utils.generator.persistence.model.GeneratorModel;
import io.xiefs.utils.generator.persistence.properties.KeyGeneratorProperties;
import io.xiefs.utils.generator.persistence.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Description:
 * Author xie
 * Date 2019/6/6 下午1:49
 */
@Component
public class KeyGeneratorService {
    private final DataSource dataSource;
    private final KeyGeneratorProperties properties;
    private final String querySql = "select max_id,step from t_key_generator where business_id = ? for update";
    private final String updateSql = "update t_key_generator set max_id = ? where business_id = ?";

    @Autowired
    public KeyGeneratorService(DataSource dataSource, KeyGeneratorProperties properties) {
        this.dataSource = dataSource;
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        List<KeyGeneratorProperties.Business> businesses = properties.getBusinesses();


        String query = "select count(1) as rows from t_key_generator where business_id = ? ";
        String insert = "insert into t_key_generator (business_id,max_id,step,description,create_time,update_time) values (?,?,?,?,now(),now())";

        businesses.forEach(business -> {
            Connection connection = null;

            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);

                ps = connection.prepareStatement(query);
                ps.setString(1, business.getBusinessId());
                rs = ps.executeQuery();
                if (!(rs.next() && Integer.valueOf(1).equals(rs.getInt("rows")))) {
                    close(rs);
                    close(ps);
                    ps = connection.prepareStatement(insert);
                    ps.setString(1, business.getBusinessId());
                    ps.setLong(2, business.getBegin());
                    ps.setInt(3, business.getStep());
                    ps.setString(4, business.getDescription());
                    ps.executeUpdate();
                }
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close(rs);
                close(ps);
                close(connection);
            }
        });
        IdUtil.init(this);

    }

    public GeneratorModel queryKey(String businessId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();

            connection.setAutoCommit(false);
            ps = connection.prepareStatement(querySql);

            ps.setString(1, businessId);

            rs = ps.executeQuery();
            GeneratorModel model = new GeneratorModel();
            if (rs.next()) {
                long maxId = rs.getLong("max_id");
                Integer step = rs.getInt("step");
                model.setBusinessId(businessId);
                model.setMinId(maxId + 1L);
                model.setMaxId(maxId + step);
                close(rs);
                close(ps);
                ps = connection.prepareStatement(updateSql);
                ps.setLong(1, model.getMaxId());
                ps.setString(2, businessId);
                ps.executeUpdate();
                connection.commit();
            }

            return model;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
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
