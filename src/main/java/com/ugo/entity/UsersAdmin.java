package com.ugo.entity;

import java.util.Date;

/**
 * @author sunshangfeng
 *admin_users
 */
public class UsersAdmin {
	private int id;//用户ID
	private	String userName;//用户名
	private String email;//邮箱
	private String phone;//联系方式
	private String password;//密码
	private Date createdAt;
	private Date updatedAt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "UsersAdmin [id=" + id + ", userName=" + userName + ", email=" + email + ", phone=" + phone
				+ ", password=" + password + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
