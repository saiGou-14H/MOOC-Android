package com.org.moocapp.entity.Response;

import com.google.gson.annotations.SerializedName;
import com.org.moocapp.entity.LearnProgressEntity;

import java.io.Serializable;
import java.util.List;

public class LearnProgresssResponse implements Serializable {


    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public List<LearnProgressEntity> data;

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

    public List<LearnProgressEntity> getData() {
        return data;
    }

    public void setData(List<LearnProgressEntity> data) {
        this.data = data;
    }
}
