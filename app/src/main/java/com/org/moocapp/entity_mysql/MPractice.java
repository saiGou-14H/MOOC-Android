package com.org.moocapp.entity_mysql;


public class MPractice {

  private long id;
  private String name;
  private String date;
  private String site;
  private String content;
  private long teaId;
  private long couId;
  private long deleted;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }


  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public long getTeaId() {
    return teaId;
  }

  public void setTeaId(long teaId) {
    this.teaId = teaId;
  }


  public long getCouId() {
    return couId;
  }

  public void setCouId(long couId) {
    this.couId = couId;
  }


  public long getDeleted() {
    return deleted;
  }

  public void setDeleted(long deleted) {
    this.deleted = deleted;
  }

}
