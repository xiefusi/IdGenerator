package com.github.xiefusi.generator.persistence.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Description:
 * Author xie
 * Date 2019/6/6 下午2:00
 */
@ConfigurationProperties(prefix = "key-generator")
public class KeyGeneratorProperties {

    private boolean dbcust = false;

    private DBProperties datasource;

    private List<Business> businesses;

    public boolean isDbcust() {
        return dbcust;
    }

    public void setDbcust(boolean dbcust) {
        this.dbcust = dbcust;
    }

    public DBProperties getDatasource() {
        return datasource;
    }

    public void setDatasource(DBProperties datasource) {
        this.datasource = datasource;
    }

    public List<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

    public static class Business {
        public Business() {
        }

        public Business(String businessId, Long begin, Integer step, String description) {
            this.businessId = businessId;
            this.begin = begin;
            this.step = step;
            this.description = description;
        }

        private String businessId;
        private Long begin = 1L;
        private Integer step = 5000;
        private String description;

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public Long getBegin() {
            return begin;
        }

        public void setBegin(Long begin) {
            this.begin = begin;
        }

        public Integer getStep() {
            return step;
        }

        public void setStep(Integer step) {
            this.step = step;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class DBProperties{

        private String driverClassName = "com.mysql.jdbc";
        private String url;
        private String username;
        private String password;

        public DBProperties() {
        }

        public DBProperties(String driverClassName, String url, String username, String password) {
            this.driverClassName = driverClassName;
            this.url = url;
            this.username = username;
            this.password = password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
