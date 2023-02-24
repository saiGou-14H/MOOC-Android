package com.org.moocapp.entity_mysql;


public class MStudentCourseCart {

  private long stuId;
  private long couId;
  private java.sql.Timestamp addTime;


  public long getStuId() {
    return stuId;
  }

  public void setStuId(long stuId) {
    this.stuId = stuId;
  }


  public long getCouId() {
    return couId;
  }

  public void setCouId(long couId) {
    this.couId = couId;
  }


  public java.sql.Timestamp getAddTime() {
    return addTime;
  }

  public void setAddTime(java.sql.Timestamp addTime) {
    this.addTime = addTime;
  }

}
