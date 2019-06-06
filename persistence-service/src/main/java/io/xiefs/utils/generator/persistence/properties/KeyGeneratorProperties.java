package io.xiefs.utils.generator.persistence.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Description:
 * Author xie
 * Date 2019/6/6 下午2:00
 */
@ConfigurationProperties(prefix = "key-generator")
public class KeyGeneratorProperties {

    private List<Business> businesses;

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
        private Integer step = 1000;
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
}
