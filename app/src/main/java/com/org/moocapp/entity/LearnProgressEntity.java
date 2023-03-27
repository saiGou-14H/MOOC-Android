package com.org.moocapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;


public  class LearnProgressEntity implements Serializable{
    @SerializedName("stuId")
    public long stuId;
    @SerializedName("couId")
    public int couId;
    @SerializedName("classId")
    public Object classId;
    @SerializedName("chaId")
    public int chaId;
    @SerializedName("chaIndex")
    public int chaIndex;
    @SerializedName("updateTime")
    public String updateTime;
    @SerializedName("nextStartTime")
    public Long nextStartTime;
    @SerializedName("readTime")
    public Long readTime;
    @SerializedName("dayTime")
    public Long dayTime;
    @SerializedName("progress")
    public double progress;

    public long getStuId() {
        return stuId;
    }

    public void setStuId(long stuId) {
        this.stuId = stuId;
    }

    public int getCouId() {
        return couId;
    }

    public void setCouId(int couId) {
        this.couId = couId;
    }

    public Object getClassId() {
        return classId;
    }

    public void setClassId(Object classId) {
        this.classId = classId;
    }

    public int getChaId() {
        return chaId;
    }

    public void setChaId(int chaId) {
        this.chaId = chaId;
    }

    public int getChaIndex() {
        return chaIndex;
    }

    public void setChaIndex(int chaIndex) {
        this.chaIndex = chaIndex;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getNextStartTime() {
        return nextStartTime;
    }

    public void setNextStartTime(Long nextStartTime) {
        this.nextStartTime = nextStartTime;
    }

    public Long getReadTime() {
        return readTime;
    }

    public void setReadTime(Long readTime) {
        this.readTime = readTime;
    }

    public Long getDayTime() {
        return dayTime;
    }

    public void setDayTime(Long dayTime) {
        this.dayTime = dayTime;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}