package com.org.moocapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class StudentCourseEntity implements Serializable {
    @SerializedName("stuId")
    public long stuId;
    @SerializedName("couId")
    public int couId;
    @SerializedName("collect")
    public boolean collect;
    @SerializedName("have")
    public boolean have;
    @SerializedName("recommend")
    public boolean recommend;
    @SerializedName("comment")
    public String comment;


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

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}