package com.ugo.entity.CouponEntity;

import java.util.Date;

public class Coupon {

    private String name; //优惠券名称
    private Integer cardId; // card_id
    private Integer faceValue; //券面值
    private Integer repertory; //库存
    private Integer getNumber; //领取数
    private Integer chargeOffNumber; //核销数
    private Integer circulation; //发行量
    private Integer useThreshold; //使用门槛
    private String copyWriter; //宣传文案
    private Date startTime; //开始时间
    private Date endTime; //结束时间
    private Date createTime; //创建时间
    private Date updateTime; //更新时间
    private Short lgDel; //逻辑删除标志

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Integer faceValue) {
        this.faceValue = faceValue;
    }

    public Integer getRepertory() {
        return repertory;
    }

    public void setRepertory(Integer repertory) {
        this.repertory = repertory;
    }

    public Integer getGetNumber() {
        return getNumber;
    }

    public void setGetNumber(Integer getNumber) {
        this.getNumber = getNumber;
    }

    public Integer getChargeOffNumber() {
        return chargeOffNumber;
    }

    public void setChargeOffNumber(Integer chargeOffNumber) {
        this.chargeOffNumber = chargeOffNumber;
    }

    public Integer getCirculation() {
        return circulation;
    }

    public void setCirculation(Integer circulation) {
        this.circulation = circulation;
    }

    public Integer getUseThreshold() {
        return useThreshold;
    }

    public void setUseThreshold(Integer useThreshold) {
        this.useThreshold = useThreshold;
    }

    public String getCopyWriter() {
        return copyWriter;
    }

    public void setCopyWriter(String copyWriter) {
        this.copyWriter = copyWriter;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
        return "Coupon{" +
                "name='" + name + '\'' +
                ", cardId=" + cardId +
                ", faceValue=" + faceValue +
                ", repertory=" + repertory +
                ", getNumber=" + getNumber +
                ", chargeOffNumber=" + chargeOffNumber +
                ", circulation=" + circulation +
                ", useThreshold=" + useThreshold +
                ", copyWriter='" + copyWriter + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", lgDel=" + lgDel +
                '}';
    }
}
