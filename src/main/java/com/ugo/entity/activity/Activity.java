package com.ugo.entity.activity;

import java.util.Date;

public class Activity {

    private String name; //活动名称

    private Short type; //活动类型：1 折扣，2 一口价，3 满单返券，4 banner活动

    private Short suitableObject; //适用对象：1 所以人，2，新用户

    private Short status = 1; //活动状态：1 进行中，2 已结束

    private String startTime; //开始时间

    private String endTime; //结束时间

    private Short InitiateMode; //启用状态：1 启用，2停用

    private Short countType; //次数类型 1：无限制，2：件/人，3：件/人/天

    private Integer count; //次数（活动每人可以享受多少次）

    private Short timeType; //生效时间类型 1：全天，2：指定时段

    private String failureTime; //生效时间

    private String deadTime; //失效时间

    private Integer sillOrder; //门槛单数（下满多少单可享受优惠）

    private Short showLocation; //展示位置：1 小程序Banner，2 设备主屏

    private Short showLogic; //展示逻辑：1 跳转，2 仅展是

    private Date createTime = new Date(); //创建时间

    private Date updateTime; //更新时间

    private Short lgDel = 1; //逻辑删除: 1 正常，2，删除

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getSuitableObject() {
        return suitableObject;
    }

    public void setSuitableObject(Short suitableObject) {
        this.suitableObject = suitableObject;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Short getInitiateMode() {
        return InitiateMode;
    }

    public void setInitiateMode(Short initiateMode) {
        InitiateMode = initiateMode;
    }

    public Short getCountType() {
        return countType;
    }

    public void setCountType(Short countType) {
        this.countType = countType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Short getTimeType() {
        return timeType;
    }

    public void setTimeType(Short timeType) {
        this.timeType = timeType;
    }

    public String getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(String failureTime) {
        this.failureTime = failureTime;
    }

    public String getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(String deadTime) {
        this.deadTime = deadTime;
    }

    public Integer getSillOrder() {
        return sillOrder;
    }

    public void setSillOrder(Integer sillOrder) {
        this.sillOrder = sillOrder;
    }

    public Short getShowLocation() {
        return showLocation;
    }

    public void setShowLocation(Short showLocation) {
        this.showLocation = showLocation;
    }

    public Short getShowLogic() {
        return showLogic;
    }

    public void setShowLogic(Short showLogic) {
        this.showLogic = showLogic;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Short getLgDel() {
        return lgDel;
    }

    public void setLgDel(Short lgDel) {
        this.lgDel = lgDel;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", suitableObject=" + suitableObject +
                ", status=" + status +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", InitiateMode=" + InitiateMode +
                ", countType=" + countType +
                ", count=" + count +
                ", timeType=" + timeType +
                ", failureTime='" + failureTime + '\'' +
                ", deadTime='" + deadTime + '\'' +
                ", sillOrder=" + sillOrder +
                ", showLocation=" + showLocation +
                ", showLogic=" + showLogic +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", lgDel=" + lgDel +
                '}';
    }
}
