package com.org.moocapp.entity.Response;

import com.google.gson.annotations.SerializedName;

public class ResponseHeader {

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @SerializedName("data")
    public String data;
}
