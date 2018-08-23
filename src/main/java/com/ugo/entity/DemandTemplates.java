/**
 * 
 */
package com.ugo.entity;

import java.util.Date;

/**
 * @author sunshangfeng
 *需求模板
 */
public class DemandTemplates {
	private int id;
	private String name;
	private int vmType;
	private Date createdAt;
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
	public int getVmType() {
		return vmType;
	}
	public void setVmType(int vmType) {
		this.vmType = vmType;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "DemandTemplates [id=" + id + ", name=" + name + ", vmType=" + vmType + ", createdAt=" + createdAt + "]";
	}
	
	
	
}
