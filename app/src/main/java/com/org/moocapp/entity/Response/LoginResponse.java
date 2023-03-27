package com.org.moocapp.entity.Response;

import com.google.gson.annotations.SerializedName;
import com.org.moocapp.entity.LoginEntity;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public Object message;
    @SerializedName("data")
    public LoginEntity data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public LoginEntity getData() {
        return data;
    }

    public void setData(LoginEntity data) {
        this.data = data;
    }
}
