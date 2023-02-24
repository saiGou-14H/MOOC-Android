package com.org.moocapp.entity.find;

import java.io.Serializable;

public class MessageEntity implements Serializable {
    /**
     * 资讯作者名称
     */
    private String username;
    /**
     * 资讯作者头像
     */
    private String headPic;
    /**
     * 资讯id（雪花id）
     */
    private Long id;

    /**
     * 资讯标题
     */
    private String title;

    /**
     * 资讯内容
     */
    private String content;

    /**
     * 资源地址
     */
    private String resourceUrl;

    /**
     * 资讯作者
     */
    private String author;

    /**
     * 发布日期
     */
    private String date;

    /**
     * 点赞数
     */
    private Integer messageLike;

    /**
     * 教师ID
     */
    private Long teaId;

    /**
     * 评论数
     */
    private Integer commentNum;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getMessageLike() {
        return messageLike;
    }

    public void setMessageLike(Integer messageLike) {
        this.messageLike = messageLike;
    }

    public Long getTeaId() {
        return teaId;
    }

    public void setTeaId(Long teaId) {
        this.teaId = teaId;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
}
