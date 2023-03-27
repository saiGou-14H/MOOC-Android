package com.org.moocapp.entity.Requests;

import com.google.gson.annotations.SerializedName;

public class StudentParcticeEntity {

    @SerializedName("stuId")
    public long stuId;
    @SerializedName("praId")
    public int praId;
    @SerializedName("praDate")
    public String praDate;
    @SerializedName("stu_name")
    public Object stuName;
    @SerializedName("phone")
    public Object phone;
    @SerializedName("time")
    public Object time;
    @SerializedName("position")
    public Object position;
    @SerializedName("remake")
    public Object remake;

    public long getStuId() {
        return stuId;
    }

    public void setStuId(long stuId) {
        this.stuId = stuId;
    }

    public int getPraId() {
        return praId;
    }

    public void setPraId(int praId) {
        this.praId = praId;
    }

    public String getPraDate() {
        return praDate;
    }

    public void setPraDate(String praDate) {
        this.praDate = praDate;
    }

    public Object getStuName() {
        return stuName;
    }

    public void setStuName(Object stuName) {
        this.stuName = stuName;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }

    public Object getRemake() {
        return remake;
    }

    public void setRemake(Object remake) {
        this.remake = remake;
    }
}
