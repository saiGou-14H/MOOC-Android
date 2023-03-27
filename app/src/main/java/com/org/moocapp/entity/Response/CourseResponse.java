package com.org.moocapp.entity.Response;

import com.google.gson.annotations.SerializedName;
import com.org.moocapp.entity.CourseEntity;

import java.io.Serializable;
import java.util.List;

public class CourseResponse implements Serializable {

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<CourseEntity> data;

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

    public List<CourseEntity> getData() {
        return data;
    }

    public void setData(List<CourseEntity> data) {
        this.data = data;
    }


}
