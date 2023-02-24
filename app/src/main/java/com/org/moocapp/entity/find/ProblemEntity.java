package com.org.moocapp.entity.find;

import java.io.Serializable;

public class ProblemEntity implements Serializable {
    /**
     * 问题作者名称
     */
    private String username;
    /**
     * 问题作者头像
     */
    private String headPic;
    /**
     * 雪花ID后八位
     */
    private Long id;

    /**
     * 问题标题
     */
    private String title;

    /**
     * 发布问题/回复的作者id
     */
    private Long auId;

    /**
     * 内容
     */
    private String content;

    /**
     * 资源地址
     */
    private String resourceUrl;

    /**
     * 1被采纳,0否
     */
    private Boolean isSelect;

    /**
     * 点赞数
     */
    private Integer likeNum;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     * 创建时间
     */
    private String createTime;

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

    public Long getAuId() {
        return auId;
    }

    public void setAuId(Long auId) {
        this.auId = auId;
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

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
