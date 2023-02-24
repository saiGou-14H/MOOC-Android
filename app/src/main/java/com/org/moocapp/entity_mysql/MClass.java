package com.org.moocapp.entity_mysql;


public class MClass {

  private long id;
  private long teaId;
  private String name;
  private long numbers;
  private String introduction;
  private long deleted;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getTeaId() {
    return teaId;
  }

  public void setTeaId(long teaId) {
    this.teaId = teaId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public long getNumbers() {
    return numbers;
  }

  public void setNumbers(long numbers) {
    this.numbers = numbers;
  }


  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }


  public long getDeleted() {
    return deleted;
  }

  public void setDeleted(long deleted) {
    this.deleted = deleted;
  }

}
