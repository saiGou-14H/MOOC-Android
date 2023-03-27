package com.org.moocapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class CourseChapterEntity implements Serializable{
    @SerializedName("id")
    public int id;
    @SerializedName("chaIndex")
    public int chaIndex;
    @SerializedName("title")
    public String title;
    @SerializedName("content")
    public String content;
    @SerializedName("courseId")
    public int courseId;
    @SerializedName("resourceUrl")
    public String resourceUrl;
    @SerializedName("time")
    public int time;
    @SerializedName("version")
    public int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChaIndex() {
        return chaIndex;
    }

    public void setChaIndex(int chaIndex) {
        this.chaIndex = chaIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}