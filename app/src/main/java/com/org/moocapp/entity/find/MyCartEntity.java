package com.org.moocapp.entity.find;

import java.io.Serializable;

public class MyCartEntity implements Serializable {
    /**
     * 是否选中
     */
    private boolean flag;
    /**
     * 学生id
     */

    private Long stuId;

    /**
     * 课程id
     */

    private Long couId;

    /**
     * 加入购物车时间
     */
    private String addTime;

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

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public Long getCouId() {
        return couId;
    }

    public void setCouId(Long couId) {
        this.couId = couId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
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
