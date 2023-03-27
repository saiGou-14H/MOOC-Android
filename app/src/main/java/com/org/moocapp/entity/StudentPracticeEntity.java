package com.org.moocapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StudentPracticeEntity implements Serializable {
        @SerializedName("stuId")
        public long stuId;
        @SerializedName("praId")
        public int praId;
        @SerializedName("praDate")
        public String praDate;
        @SerializedName("stuName")
        public String stuName;
        @SerializedName("phone")
        public String phone;
        @SerializedName("time")
        public String time;
        @SerializedName("position")
        public String position;
        @SerializedName("remake")
        public String remake;

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

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }
}
