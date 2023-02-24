package com.org.moocapp.entity_mysql;


public class MLearnProgress {

  private long stuId;
  private long couId;
  private long classId;
  private long chaId;
  private long chaIndex;
  private String updateTime;
  private String startTime;
  private String endTime;
  private String readTime;
  private String dayTime;


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


  public long getClassId() {
    return classId;
  }

  public void setClassId(long classId) {
    this.classId = classId;
  }


  public long getChaId() {
    return chaId;
  }

  public void setChaId(long chaId) {
    this.chaId = chaId;
  }


  public long getChaIndex() {
    return chaIndex;
  }

  public void setChaIndex(long chaIndex) {
    this.chaIndex = chaIndex;
  }


  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }


  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }


  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }


  public String getReadTime() {
    return readTime;
  }

  public void setReadTime(String readTime) {
    this.readTime = readTime;
  }


  public String getDayTime() {
    return dayTime;
  }

  public void setDayTime(String dayTime) {
    this.dayTime = dayTime;
  }

}
