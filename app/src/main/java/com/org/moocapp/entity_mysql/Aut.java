package com.org.moocapp.entity_mysql;


public class Aut {

  private long id;
  private long autId;
  private String email;
  private String password;
  private String username;
  private String openid;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getAutId() {
    return autId;
  }

  public void setAutId(long autId) {
    this.autId = autId;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

}
