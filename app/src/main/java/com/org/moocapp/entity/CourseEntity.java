package com.org.moocapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CourseEntity implements Serializable{


    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("teaId")
    public long teaId;
    @SerializedName("couType")
    public String couType;
    @SerializedName("integral")
    public int integral;
    @SerializedName("introduction")
    public String introduction;
    @SerializedName("cataNum")
    public int cataNum;
    @SerializedName("collNum")
    public int collNum;
    @SerializedName("recoNum")
    public int recoNum;
    @SerializedName("picture")
    public String picture;
    @SerializedName("share")
    public int share;
    @SerializedName("version")
    public int version;
    @SerializedName("time")
    public Long time;
    @SerializedName("username")
    public String username;
    @SerializedName("headPic")
    public String headPic;
    @SerializedName("facePic")
    public String facePic;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTeaId() {
        return teaId;
    }

    public void setTeaId(long teaId) {
        this.teaId = teaId;
    }

    public String getCouType() {
        return couType;
    }

    public void setCouType(String couType) {
        this.couType = couType;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getCataNum() {
        return cataNum;
    }

    public void setCataNum(int cataNum) {
        this.cataNum = cataNum;
    }

    public int getCollNum() {
        return collNum;
    }

    public void setCollNum(int collNum) {
        this.collNum = collNum;
    }

    public int getRecoNum() {
        return recoNum;
    }

    public void setRecoNum(int recoNum) {
        this.recoNum = recoNum;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
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