package com.org.moocapp.entity_mysql;


public class MMessageComment {

  private long id;
  private long cId;
  private long messageId;
  private String content;
  private String date;
  private long isLike;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getCId() {
    return cId;
  }

  public void setCId(long cId) {
    this.cId = cId;
  }


  public long getMessageId() {
    return messageId;
  }

  public void setMessageId(long messageId) {
    this.messageId = messageId;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }


  public long getIsLike() {
    return isLike;
  }

  public void setIsLike(long isLike) {
    this.isLike = isLike;
  }

}
