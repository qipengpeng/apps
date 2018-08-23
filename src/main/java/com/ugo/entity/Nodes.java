/**
 * 
 */
package com.ugo.entity;

import java.util.Date;
import java.util.List;

/**
 * @author sunshangfeng
 *点位表
 */
public class Nodes {
	private int id;//点位ID
	private String name;//点位名称
	private String address;//地址
	private float latitude;//经度
	private float longitude;//纬度
	private int regionId;//区县ID
	private int type;//-点位类型    1.写字楼2.商场3.学校...
	private int population;//-
	private int ambient;//-
	private Date createdAt;//-
	private int state;//点位运营状态
	private List<NodeVms>nodeVmsList;//点位设备
	private int percentage;
	
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public List<NodeVms> getNodeVmsList() {
		return nodeVmsList;
	}
	public void setNodeVmsList(List<NodeVms> nodeVmsList) {
		this.nodeVmsList = nodeVmsList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public int getAmbient() {
		return ambient;
	}
	public void setAmbient(int ambient) {
		this.ambient = ambient;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "Nodes [id=" + id + ", name=" + name + ", address=" + address + ", latitude=" + latitude + ", longitude="
				+ longitude + ", regionId=" + regionId + ", type=" + type + ", population=" + population + ", ambient="
				+ ambient + ", createdAt=" + createdAt + ", state=" + state + ", nodeVmsList=" + nodeVmsList + "]";
	}
	
}
