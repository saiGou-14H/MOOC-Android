package com.org.moocapp.entity_mysql;


public class MStudentPractice {

  private long stuId;
  private long praId;
  private String praDate;


  public long getStuId() {
    return stuId;
  }

  public void setStuId(long stuId) {
    this.stuId = stuId;
  }


  public long getPraId() {
    return praId;
  }

  public void setPraId(long praId) {
    this.praId = praId;
  }


  public String getPraDate() {
    return praDate;
  }

  public void setPraDate(String praDate) {
    this.praDate = praDate;
  }

}
