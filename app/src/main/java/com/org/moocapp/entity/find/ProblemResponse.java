package com.org.moocapp.entity.find;

import java.io.Serializable;

public class ProblemResponse implements Serializable {
    private String msg;
    private int code;
    private ProblemEntity data;

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

    public ProblemEntity getData() {
        return data;
    }

    public void setData(ProblemEntity data) {
        this.data = data;
    }
}
