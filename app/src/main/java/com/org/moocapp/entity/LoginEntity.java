package com.org.moocapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class LoginEntity implements Serializable {
    @SerializedName("role")
    public int role;
    @SerializedName("staffno")
    public long staffno;
    @SerializedName("staffname")
    public String staffname;
    @SerializedName("token")
    public String token;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public long getStaffno() {
        return staffno;
    }

    public void setStaffno(long staffno) {
        this.staffno = staffno;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}