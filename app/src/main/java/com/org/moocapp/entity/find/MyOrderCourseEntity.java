package com.org.moocapp.entity.find;

import java.io.Serializable;

public class MyOrderCourseEntity implements Serializable {
    /**
     * 订单创建时间
     */
    private String createTime;
    /**
     * 课程名称
     */
    private String name;
    /**
     * 课程积分（价格）
     */
    private Integer integral;

    /**
     * 课程封面图片
     */
    private String picture;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
