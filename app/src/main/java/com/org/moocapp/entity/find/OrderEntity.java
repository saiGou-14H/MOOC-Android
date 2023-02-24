package com.org.moocapp.entity.find;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrderEntity implements Serializable {
    /**
     * 订单编号，雪花ID
     */
    private Long id;

    /**
     * 学生id
     */
    private Long stuId;

    /**
     * 支付状态
     * 0未支付 1为已支付
     */
    private Boolean payState;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 课程总价
     */
    private Integer allIntegral;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public Boolean getPayState() {
        return payState;
    }

    public void setPayState(Boolean payState) {
        this.payState = payState;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAllIntegral() {
        return allIntegral;
    }

    public void setAllIntegral(Integer allIntegral) {
        this.allIntegral = allIntegral;
    }
}
