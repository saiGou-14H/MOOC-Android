package com.org.moocapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IntegralHistoryEntity implements Serializable {
    @SerializedName("stuId")
    public long stuId;
    @SerializedName("origin")
    public String origin;
    @SerializedName("integralchild")
    public int integralchild;
    @SerializedName("type")
    public boolean type;
    @SerializedName("time")
    public String time;

    public long getStuId() {
        return stuId;
    }

    public void setStuId(long stuId) {
        this.stuId = stuId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getIntegralchild() {
        return integralchild;
    }

    public void setIntegralchild(int integralchild) {
        this.integralchild = integralchild;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}