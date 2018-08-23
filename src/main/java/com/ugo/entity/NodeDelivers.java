/**
 * 
 */
package com.ugo.entity;

import java.util.Arrays;


/**
 * @author sunshangfeng
 *线路点位关联
 */
public class NodeDelivers {
	private int routeId;//线路ID
	private String routeName;//人员名称
	private int nodeId;//点位ID
	private String nodeName;//人员名称
	private int principalId;//人员ID
	private String principalName;//人员名称
	private int[] principals;//人员ID
	private String createdAt;
	
	
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public int[] getPrincipals() {
		return principals;
	}

	public void setPrincipals(int[] principals) {
		this.principals = principals;
	}

	public NodeDelivers() {
	}

	public NodeDelivers(int routeId,int nodeId,String nodeName, int principalId,String principalName) {
		super();
		this.routeId = routeId;
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.principalId = principalId;
		this.principalName = principalName;
	}

	public int getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(int principalId) {
		this.principalId = principalId;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "NodeDelivers [routeId=" + routeId + ", routeName=" + routeName + ", nodeId=" + nodeId + ", nodeName="
				+ nodeName + ", principalId=" + principalId + ", principalName=" + principalName + ", principals="
				+ Arrays.toString(principals) + ", createdAt=" + createdAt + "]";
	}
	
}
