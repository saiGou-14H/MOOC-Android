package com.org.moocapp.entity.Response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserEntity implements Serializable {

    @SerializedName("id")
    public long id;
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("role")
    public int role;
    @SerializedName("age")
    public int age;
    @SerializedName("email")
    public String email;
    @SerializedName("phone")
    public String phone;
    @SerializedName("studyTime")
    public int studyTime;
    @SerializedName("integral")
    public int integral;
    @SerializedName("position")
    public Object position;
    @SerializedName("headPic")
    public String headPic;
    @SerializedName("facePic")
    public String facePic;
    @SerializedName("introduction")
    public Object introduction;
    @SerializedName("sign")
    public String sign;
    @SerializedName("issign")
    public boolean issign;
    @SerializedName("todayStudyTime")
    public int todayStudyTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(int studyTime) {
        this.studyTime = studyTime;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
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

    public Object getIntroduction() {
        return introduction;
    }

    public void setIntroduction(Object introduction) {
        this.introduction = introduction;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public boolean isIssign() {
        return issign;
    }

    public void setIssign(boolean issign) {
        this.issign = issign;
    }

    public int getTodayStudyTime() {
        return todayStudyTime;
    }

    public void setTodayStudyTime(int todayStudyTime) {
        this.todayStudyTime = todayStudyTime;
    }
}