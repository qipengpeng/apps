package com.ugo.entity.activity.dto;


import com.ugo.entity.activity.ActivityPT;
import com.ugo.entity.activity.ActivityProduct;

import java.util.List;

public class DiscountActivityDTO {

    private String name; //活动名称

    private Integer obj; //适用对象 1：所有人， 2：新用户

    private Integer num; //参与次数选择 1：无限制，2：件/人，3：件/人/天

    private Integer numText; //参与次数：件/人

    private Integer numDayText; //参与次数：件/人/天

    private List<ActivityPT> activityPTs; //活动点位集合

    private List<ActivityProduct> activityProducts; //活动商品集合

    private String startDate; //起始时间

    private String endDate;  //结束时间

    private Integer time; //生效时间选择 1：全天，2：指定时段

    private String startTime; //指定时段：生效时间

    private String endTime; //指定时段：失效时间

    private Short type = 1; //活动类型

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getObj() {
        return obj;
    }

    public void setObj(Integer obj) {
        this.obj = obj;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNumText() {
        return numText;
    }

    public void setNumText(Integer numText) {
        this.numText = numText;
    }

    public Integer getNumDayText() {
        return numDayText;
    }

    public void setNumDayText(Integer numDayText) {
        this.numDayText = numDayText;
    }

    public List<ActivityPT> getActivityPTs() {
        return activityPTs;
    }

    public void setActivityPTs(List<ActivityPT> activityPTs) {
        this.activityPTs = activityPTs;
    }

    public List<ActivityProduct> getActivityProducts() {
        return activityProducts;
    }

    public void setActivityProducts(List<ActivityProduct> activityProducts) {
        this.activityProducts = activityProducts;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
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

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DiscountActivityDTO{" +
                "name='" + name + '\'' +
                ", obj=" + obj +
                ", num=" + num +
                ", numText=" + numText +
                ", numDayText=" + numDayText +
                ", activityPTs=" + activityPTs +
                ", activityProducts=" + activityProducts +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", time=" + time +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", type=" + type +
                '}';
    }
}
