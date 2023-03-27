package com.org.moocapp.entity.Response;

import com.google.gson.annotations.SerializedName;
import com.org.moocapp.entity.StudentCourseEntity;

import java.io.Serializable;

public class StudentCourseResponse implements Serializable {

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public StudentCourseEntity data;

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

    public StudentCourseEntity getData() {
        return data;
    }

    public void setData(StudentCourseEntity data) {
        this.data = data;
    }


}
