/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 *需求单点位信息
 */
public class DeliversNode {
	private String id ;
	private String name ;
	private String lineId ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	@Override
	public String toString() {
		return "Delivers [id=" + id + ", name=" + name + ", lineId=" + lineId + "]";
	}
	
}
