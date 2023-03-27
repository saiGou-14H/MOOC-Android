package com.org.moocapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CourseCommentEntity implements Serializable{
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
    @SerializedName("commentTime")
    public Object commentTime;
    @SerializedName("username")
    public String username;
    @SerializedName("headPic")
    public String headPic;
    @SerializedName("facePic")
    public String facePic;

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

    public Object getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Object commentTime) {
        this.commentTime = commentTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getFacePic() {
        return facePic;
    }

    public void setFacePic(String facePic) {
        this.facePic = facePic;
    }
}