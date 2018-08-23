/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 *
 */
public class MqttUser {
	private int id;//
	private int isSuperuser;//
	private String username;//
	private String password;//
	private String salt;//
	private String created;//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIsSuperuser() {
		return isSuperuser;
	}
	public void setIsSuperuser(int isSuperuser) {
		this.isSuperuser = isSuperuser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	
}
