/**
 * 
 */
package com.ugo.entity;

import java.util.List;

/**
 * @author sunshangfeng
 *配送人员线路点位
 */
public class RouteDetails {
	private int id;//线路ID
	private String name;//线路名称
	private int principalId;//负责人ID
	private String principal;//负责人
	private String includeSite;//包含点位
	private List<NodeDelivers> nodeIdList;//点位
	
	public List<NodeDelivers> getNodeIdList() {
		return nodeIdList;
	}
	public void setNodeIdList(List<NodeDelivers> nodeIdList) {
		this.nodeIdList = nodeIdList;
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
	
	public int getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(int principalId) {
		this.principalId = principalId;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getIncludeSite() {
		return includeSite;
	}
	public void setIncludeSite(String includeSite) {
		this.includeSite = includeSite;
	}
	@Override
	public String toString() {
		return "RouteDetails [id=" + id + ", name=" + name + ", principalId=" + principalId + ", principal=" + principal
				+ ", includeSite=" + includeSite + ", nodeIdList=" + nodeIdList + "]";
	}
	
}
