package com.ugo.entity;


/**
 * @author sunshangfeng
 *用户表
 */
public class Users {
	
	 private int id;
	 
	 private String openId;//
	 private String uuid;
	 private String nickname;
	 private String headimgurl;
	 private String phone;
	 private String createtime;
	 private String uboxPhone;
	 
	public String getUboxPhone() {
		return uboxPhone;
	}
	public void setUboxPhone(String uboxPhone) {
		this.uboxPhone = uboxPhone;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", openId=" + openId + ", uuid=" + uuid + ", nickname=" + nickname + ", headimgurl="
				+ headimgurl + ", phone=" + phone + ", createtime=" + createtime + ", uboxPhone=" + uboxPhone + "]";
	}
}
