package com.org.moocapp.entity_mysql;


public class MStudentCourseOrder {

  private long id;
  private long stuId;
  private long payState;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp payTime;
  private String remark;
  private long allIntegral;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getStuId() {
    return stuId;
  }

  public void setStuId(long stuId) {
    this.stuId = stuId;
  }


  public long getPayState() {
    return payState;
  }

  public void setPayState(long payState) {
    this.payState = payState;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public java.sql.Timestamp getPayTime() {
    return payTime;
  }

  public void setPayTime(java.sql.Timestamp payTime) {
    this.payTime = payTime;
  }


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }


  public long getAllIntegral() {
    return allIntegral;
  }

  public void setAllIntegral(long allIntegral) {
    this.allIntegral = allIntegral;
  }

}
