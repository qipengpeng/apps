package com.ugo.entity.CouponEntity;

import java.util.Date;

public class AccessToken {

    private Integer id; //id
    private String token; //access_token值
    private Integer expires; //持续时间
    private Date createTime; //创建时间
    private Date updateTime; //更新时间
    private Short lgDel; //逻辑删除标志


    public AccessToken() {
    }

    public AccessToken(Integer id, String token, Integer expires, Date createTime, Date updateTime, Short lgDel) {
        this.id = id;
        this.token = token;
        this.expires = expires;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.lgDel = lgDel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpires() {
        return expires;
    }

    public void setExpires(Integer expires) {
        this.expires = expires;
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
        return "AccessToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expires=" + expires +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", lgDel=" + lgDel +
                '}';
    }
}
