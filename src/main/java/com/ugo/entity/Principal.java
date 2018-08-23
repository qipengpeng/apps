/**
 * 
 */
package com.ugo.entity;

import java.util.List;

/**
 * @author sunshangfeng
 *配送人员
 */
public class Principal {
	private int id;//人员ID
	private String name;//人员名称
	private List<Nodes>nodeList;//点位列表
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
	public List<Nodes> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<Nodes> nodeList) {
		this.nodeList = nodeList;
	}
	@Override
	public String toString() {
		return "Principal [id=" + id + ", name=" + name + ", nodeList=" + nodeList + "]";
	}
	
}
