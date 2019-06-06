package io.xiefs.utils.generator.persistence.model;

/**
 * Description:
 * Author xie
 * Date 2019/6/6 下午1:37
 */
public class GeneratorModel {
    private String businessId;
    private Long minId;
    private Long maxId;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Long getMinId() {
        return minId;
    }

    public void setMinId(Long minId) {
        this.minId = minId;
    }

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

  
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GeneratorModel{");
        sb.append("businessId='").append(businessId).append('\'');
        sb.append(", minId=").append(minId);
        sb.append(", maxId=").append(maxId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
