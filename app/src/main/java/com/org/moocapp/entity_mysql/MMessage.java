package com.org.moocapp.entity_mysql;


public class MMessage {

  private long id;
  private String title;
  private String content;
  private String resourceUrl;
  private long author;
  private java.sql.Timestamp date;
  private long messageLike;
  private long teaId;
  private long commentNum;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public String getResourceUrl() {
    return resourceUrl;
  }

  public void setResourceUrl(String resourceUrl) {
    this.resourceUrl = resourceUrl;
  }


  public long getAuthor() {
    return author;
  }

  public void setAuthor(long author) {
    this.author = author;
  }


  public java.sql.Timestamp getDate() {
    return date;
  }

  public void setDate(java.sql.Timestamp date) {
    this.date = date;
  }


  public long getMessageLike() {
    return messageLike;
  }

  public void setMessageLike(long messageLike) {
    this.messageLike = messageLike;
  }


  public long getTeaId() {
    return teaId;
  }

  public void setTeaId(long teaId) {
    this.teaId = teaId;
  }


  public long getCommentNum() {
    return commentNum;
  }

  public void setCommentNum(long commentNum) {
    this.commentNum = commentNum;
  }

}
