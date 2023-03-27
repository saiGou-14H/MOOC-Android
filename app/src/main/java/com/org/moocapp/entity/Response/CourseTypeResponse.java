package com.org.moocapp.entity.Response;

import com.google.gson.annotations.SerializedName;
import com.org.moocapp.entity.CourseTypeEntity;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CourseTypeResponse {

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public List<CourseTypeEntity> data;

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

    public List<CourseTypeEntity> getData() {
        return data;
    }

    public void setData(List<CourseTypeEntity> data) {
        this.data = data;
    }
}
