package com.org.moocapp.entity_mysql;


public class MStudentClass {

  private long stuId;
  private long classId;
  private java.sql.Timestamp joinDate;


  public long getStuId() {
    return stuId;
  }

  public void setStuId(long stuId) {
    this.stuId = stuId;
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
