package com.org.moocapp.entity_mysql;


public class MUser {

  private long id;
  private String username;
  private String password;
  private long role;
  private long age;
  private String email;
  private String phone;
  private long studyTime;
  private long integral;
  private String position;
  private String headPic;
  private String facePic;
  private String introduction;
  private String sign;


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


  public long getRole() {
    return role;
  }

  public void setRole(long role) {
    this.role = role;
  }


  public long getAge() {
    return age;
  }

  public void setAge(long age) {
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


  public long getStudyTime() {
    return studyTime;
  }

  public void setStudyTime(long studyTime) {
    this.studyTime = studyTime;
  }


  public long getIntegral() {
    return integral;
  }

  public void setIntegral(long integral) {
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

}
