package com.github.xiefusi.generator.persistence.model;

/**
 * Description:
 * Author xie
 * Date 2019/6/6 下午1:37
 */
public class GeneratorModel {
    private Long id;
    private String businessId;
    private long maxId;
    private int step;
    private String updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public long getMaxId() {
        return maxId;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }


    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GeneratorModel{");
        sb.append("businessId='").append(businessId).append('\'');
        sb.append(", maxId=").append(maxId);
        sb.append(", step=").append(step);
        sb.append(", updateTime='").append(updateTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
