package com.org.moocapp.entity.find;

import java.io.Serializable;
import java.util.List;

public class MyMQuestionCommentListResponse implements Serializable {
    private String msg;
    private int code;
    private List<MyMQuestionCommentEntity> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<MyMQuestionCommentEntity> getData() {
        return data;
    }

    public void setData(List<MyMQuestionCommentEntity> data) {
        this.data = data;
    }
}
