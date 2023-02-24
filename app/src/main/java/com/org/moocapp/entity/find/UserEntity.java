package com.org.moocapp.entity.find;

import java.io.Serializable;

public class UserEntity implements Serializable {
    /**
     * 班级ID，雪花ID后8位
     */
    private Long id;

    /**
     * 真实姓名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 1为学生，2为教师
     * 默认为学生
     */
    private Integer role;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * qq邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 学习时长
     */
    private Long studyTime;

    /**
     * 学习积分
     */
    private Integer integral;

    /**
     * 职务
     */
    private String position;

    /**
     * 头像
     */
    private String headPic;

    /**
     * 人脸
     */
    private String facePic;

    /**
     * 个人介绍
     */
    private String introduction;

    /**
     * 周打卡，默认为7个0
     */
    private String sign;

    /**
     * 单天是否签到
     */
    private Boolean issign;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
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

    public Long getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(Long studyTime) {
        this.studyTime = studyTime;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Boolean getIssign() {
        return issign;
    }

    public void setIssign(Boolean issign) {
        this.issign = issign;
    }
}
