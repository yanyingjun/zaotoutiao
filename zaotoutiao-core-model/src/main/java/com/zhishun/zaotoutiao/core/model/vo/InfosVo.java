/**
 * @company 杭州智顺文化传播有限公司
 * @copyright Copyright (c) 2017 - 2018
 */


package com.zhishun.zaotoutiao.core.model.vo;

import com.zhishun.zaotoutiao.core.model.entity.Infos;

import java.util.Date;

/**
 * @author BugMan
 * @version $Id: InfosVo, v0.1 2018年02月26日 13:37BugMan Exp $
 * 返回的视频新闻列表视图
 */


public class InfosVo extends Infos{

    //排序时间
    private Date orderTime;

    //自己是否点赞
    private boolean isMyLike;

    //评论数
    private int commentsNum;

    //user
    private String headPath;

    private String nickName;

    //user_comment
    private int commentsId;

    private String content;

    private int likes;

    private Date createDate;

    /**
     * 收藏相关
     */
    private int userCollectId;

    private Date collectCreateDate;

    //channels.name as type
    private String type;

    public int getUserCollectId() {
        return userCollectId;
    }

    public void setUserCollectId(int userCollectId) {
        this.userCollectId = userCollectId;
    }

    public Date getCollectCreateDate() {
        return collectCreateDate;
    }

    public void setCollectCreateDate(Date collectCreateDate) {
        this.collectCreateDate = collectCreateDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }

    public boolean isMyLike() {
        return isMyLike;
    }

    public void setMyLike(boolean myLike) {
        isMyLike = myLike;
    }

    public int getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(int commentsId) {
        this.commentsId = commentsId;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 连续阅读时间
     */
    private String readContinuousDay;

    /**
     * 阅读时间
     */
    private String readCreateDate;

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * Getter method for property <tt>readContinuousDay</tt>.
     *
     * @return property value of readContinuousDay
     */
    public String getReadContinuousDay() {
        return readContinuousDay;
    }

    /**
     * Setter method for property <tt>readContinuousDay</tt>.
     *
     * @param readContinuousDay value to be assigned to property readContinuousDay
     */
    public void setReadContinuousDay(String readContinuousDay) {
        this.readContinuousDay = readContinuousDay;
    }

    /**
     * Getter method for property <tt>readCreateDate</tt>.
     *
     * @return property value of readCreateDate
     */
    public String getReadCreateDate() {
        return readCreateDate;
    }

    /**
     * Setter method for property <tt>readCreateDate</tt>.
     *
     * @param readCreateDate value to be assigned to property readCreateDate
     */
    public void setReadCreateDate(String readCreateDate) {
        this.readCreateDate = readCreateDate;
    }
}
