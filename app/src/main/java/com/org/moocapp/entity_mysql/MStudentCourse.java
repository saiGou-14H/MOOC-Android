package com.org.moocapp.entity_mysql;


public class MStudentCourse {

  private long stuId;
  private long couId;
  private long collect;
  private long have;
  private long recommend;
  private String comment;

  public MStudentCourse(String comment){
    this.comment = comment;
  }

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


  public long getCollect() {
    return collect;
  }

  public void setCollect(long collect) {
    this.collect = collect;
  }


  public long getHave() {
    return have;
  }

  public void setHave(long have) {
    this.have = have;
  }


  public long getRecommend() {
    return recommend;
  }

  public void setRecommend(long recommend) {
    this.recommend = recommend;
  }


  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

}
