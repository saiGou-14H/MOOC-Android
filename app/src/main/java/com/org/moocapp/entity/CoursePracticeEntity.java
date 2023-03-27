package com.org.moocapp.entity;

import com.google.gson.annotations.SerializedName;

public  class CoursePracticeEntity {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("date")
    public String date;
    @SerializedName("site")
    public String site;
    @SerializedName("content")
    public String content;
    @SerializedName("teaId")
    public long teaId;
    @SerializedName("couId")
    public int couId;
    @SerializedName("deleted")
    public boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTeaId() {
        return teaId;
    }

    public void setTeaId(long teaId) {
        this.teaId = teaId;
    }

    public int getCouId() {
        return couId;
    }

    public void setCouId(int couId) {
        this.couId = couId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
