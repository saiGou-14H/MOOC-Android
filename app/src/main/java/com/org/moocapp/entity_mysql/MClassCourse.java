package com.org.moocapp.entity_mysql;


public class MClassCourse {

  private long couId;
  private long classId;
  private java.sql.Timestamp joinDate;


  public long getCouId() {
    return couId;
  }

  public void setCouId(long couId) {
    this.couId = couId;
  }


  public long getClassId() {
    return classId;
  }

  public void setClassId(long classId) {
    this.classId = classId;
  }


  public java.sql.Timestamp getJoinDate() {
    return joinDate;
  }

  public void setJoinDate(java.sql.Timestamp joinDate) {
    this.joinDate = joinDate;
  }

}
