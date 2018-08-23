/**
 * 
 */
package com.ugo.entity;

import java.util.List;

/**
 * @author sunshangfeng
 *清货报损
 */
public class Inventory {
	private int id;//
	private int taskId;//补货任务ID
	private int demandId;//需求ID---对应交割单ID
	private int routeId;//线路id
	private int operatorId;//人员id
	private List<Nodes> nodesList;//
	
	public List<Nodes> getNodesList() {
		return nodesList;
	}
	public void setNodesList(List<Nodes> nodesList) {
		this.nodesList = nodesList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getDemandId() {
		return demandId;
	}
	public void setDemandId(int demandId) {
		this.demandId = demandId;
	}
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public int getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}
	@Override
	public String toString() {
		return "Inventory [id=" + id + ", taskId=" + taskId + ", demandId=" + demandId + ", routeId=" + routeId
				+ ", operatorId=" + operatorId + "]";
	}
	
}
