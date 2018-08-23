/**
 * 
 */
package com.ugo.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sunshangfeng
 *机器表
 */
public class Vendors {
	private int id;//设备ID
	private String password;//设备密码
	private String nameplate;//铭牌号
	private int type;//设备类型
	private int producer;//厂商
	private int nodeId;//点位ID
	private String nodeName;//点位名称
	private int state;//设备状态
	private int nodeState;//点位设备状态
	private String version;//设备系统版本
	private String versionCode;//设备系统版本
	private String createdAt;				//创建时间
	private String updatedAt;				//修改时间 
	
	
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = "";
		try {
			 format = fmt.format(fmt.parse(updatedAt));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.updatedAt = format;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getNodeState() {
		return nodeState;
	}
	public void setNodeState(int nodeState) {
		this.nodeState = nodeState;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNameplate() {
		return nameplate;
	}
	public void setNameplate(String nameplate) {
		this.nameplate = nameplate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getProducer() {
		return producer;
	}
	public void setProducer(int producer) {
		this.producer = producer;
	}
	@Override
	public String toString() {
		return "Vendors [id=" + id + ", password=" + password + ", nameplate=" + nameplate + ", type=" + type
				+ ", producer=" + producer + ", nodeId=" + nodeId + ", nodeName=" + nodeName + ", state=" + state
				+ ", nodeState=" + nodeState + ", version=" + version + "]";
	}
	
}
