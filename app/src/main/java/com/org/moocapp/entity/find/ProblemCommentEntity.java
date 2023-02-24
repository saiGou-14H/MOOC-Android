package com.org.moocapp.entity.find;

import java.io.Serializable;

public class ProblemCommentEntity implements Serializable {

    /**
     * 回复ID
     */
    private Integer id;

    /**
     * 评论者名称
     */
    private String username;
    /**
     * 评论者头像
     */
    private String headPic;


    /**
     * 回复者ID
     */
    private Long userId;

    /**
     * 问题ID
     */
    private Long questionId;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 回复时间
     */
    private String date;

    /**
     * 点赞数
     */
    private String like;

    /**
     * 是否最佳回答
     */
    private Boolean isBest;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public Boolean getBest() {
        return isBest;
    }

    public void setBest(Boolean best) {
        isBest = best;
    }
}
