package com.zhishun.zaotoutiao.core.model.entity;

import java.io.Serializable;
import java.util.Date;

public class Infos implements Serializable{

    private static final long serialVersionUID = -731339082387107210L;

    private Long id;

    private String infoId;

    private String infoType;

    private String channelId;

    private String catInfoName;

    private Integer imgType;

    private Date publishTime;

    private String source;

    private String thumbnails;

    private String title;

    private Date updateTime;

    private String videos;

    private String summary;

    private String content;

    private Date createDate;

    private Byte isHot;

    private Date pushDate;

    private Integer firstLevel;

    private Integer twoLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType == null ? null : infoType.trim();
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId == null ? null : channelId.trim();
    }

    public Integer getImgType() {
        return imgType;
    }

    public void setImgType(Integer imgType) {
        this.imgType = imgType;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails == null ? null : thumbnails.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos == null ? null : videos.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }

    public Integer getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(Integer firstLevel) {
        this.firstLevel = firstLevel;
    }

    public Integer getTwoLevel() {
        return twoLevel;
    }

    public void setTwoLevel(Integer twoLevel) {
        this.twoLevel = twoLevel;
    }

    /**
     * Getter method for property <tt>infoId</tt>.
     *
     * @return property value of infoId
     */
    public String getInfoId() {
        return infoId;
    }

    /**
     * Setter method for property <tt>infoId</tt>.
     *
     * @param infoId value to be assigned to property infoId
     */
    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    /**
     * Getter method for property <tt>catInfoName</tt>.
     *
     * @return property value of catInfoName
     */
    public String getCatInfoName() {
        return catInfoName;
    }

    /**
     * Setter method for property <tt>catInfoName</tt>.
     *
     * @param catInfoName value to be assigned to property catInfoName
     */
    public void setCatInfoName(String catInfoName) {
        this.catInfoName = catInfoName;
    }

    /**
     * Getter method for property <tt>content</tt>.
     *
     * @return property value of content
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter method for property <tt>content</tt>.
     *
     * @param content value to be assigned to property content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter method for property <tt>isHot</tt>.
     *
     * @return property value of isHot
     */
    public Byte getIsHot() {
        return isHot;
    }

    /**
     * Setter method for property <tt>isHot</tt>.
     *
     * @param isHot value to be assigned to property isHot
     */
    public void setIsHot(Byte isHot) {
        this.isHot = isHot;
    }
}