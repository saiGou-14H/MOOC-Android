package com.org.moocapp.entity.find;

import java.io.Serializable;

public class MessageCommentEntity implements Serializable {
    /**
     * 评论id（评论下再评论时才用到）
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
     * 评论者ID
     */
    private Long userId;

    /**
     * 资讯ID
     */

    private Long messageId;

    /**
     * 评论内容
     */

    private String content;

    /**
     * 评论时间
     */

    private String date;

    /**
     * 是否点赞
     */

    private Boolean isLike;

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

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
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

    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }
}
