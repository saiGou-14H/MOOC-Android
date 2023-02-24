package com.org.moocapp.entity_mysql;


public class MQuestionAnswer {

  private long id;
  private long auId;
  private String content;
  private String resourceUrl;
  private java.sql.Timestamp creatTime;
  private long isQuestion;
  private long answerQaId;
  private long isSelect;
  private String ip;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getAuId() {
    return auId;
  }

  public void setAuId(long auId) {
    this.auId = auId;
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


  public java.sql.Timestamp getCreatTime() {
    return creatTime;
  }

  public void setCreatTime(java.sql.Timestamp creatTime) {
    this.creatTime = creatTime;
  }


  public long getIsQuestion() {
    return isQuestion;
  }

  public void setIsQuestion(long isQuestion) {
    this.isQuestion = isQuestion;
  }


  public long getAnswerQaId() {
    return answerQaId;
  }

  public void setAnswerQaId(long answerQaId) {
    this.answerQaId = answerQaId;
  }


  public long getIsSelect() {
    return isSelect;
  }

  public void setIsSelect(long isSelect) {
    this.isSelect = isSelect;
  }


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

}
