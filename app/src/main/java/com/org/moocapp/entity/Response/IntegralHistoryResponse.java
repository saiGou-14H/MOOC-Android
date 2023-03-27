package com.org.moocapp.entity.Response;

import com.google.gson.annotations.SerializedName;
import com.org.moocapp.entity.IntegralHistoryEntity;

import java.io.Serializable;
import java.util.List;

public class IntegralHistoryResponse implements Serializable {

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public List<IntegralHistoryEntity> data;

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

    public List<IntegralHistoryEntity> getData() {
        return data;
    }

    public void setData(List<IntegralHistoryEntity> data) {
        this.data = data;
    }
}
