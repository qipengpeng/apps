/**
 * 
 */
package com.ugo.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author sunshangfeng
 *点位需求列表
 */
public class Demands {
	private int id;//需求ID
	private String demandDate;//需求时间
	private int  state;//需求状态
	private int type;//需求类型
	private String operator;//下单人
	private String createdAt;

	private List<Nodes>nodesList;//包含点位对象
	


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDemandDate() {
		return demandDate;
	}
	public void setDemandDate(String demandDate) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String format = "";
		try {
			 format = fmt.format(fmt.parse(demandDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.demandDate = format;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = "";
		try {
			 format = fmt.format(fmt.parse(createdAt));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.createdAt = format;
	}
	
	public List<Nodes> getNodesList() {
		return nodesList;
	}
	public void setNodesList(List<Nodes> nodesList) {
		this.nodesList = nodesList;
	}
	@Override
	public String toString() {
		return "Demands [id=" + id + ", demandDate=" + demandDate + ", state=" + state + ", type=" + type
				+ ", operator=" + operator + ", createdAt=" + createdAt + ", nodesList=" + nodesList + "]";
	}
	
	
}
