/**
 * 
 */
package com.ugo.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author sunshangfeng
 *补货任务
 */
public class ReplenishmentTasks {
	private int id;
	private int demandId;//需求ID
	private String demandDate;//需求日期
	private int state;//补货状态
	private int operatorId;//补货人ID
	private String operator;//补货人姓名
	private List<ReplenishmentDetails>replenishmentDetailsList;
	
	public List<ReplenishmentDetails> getReplenishmentDetailsList() {
		return replenishmentDetailsList;
	}
	public void setReplenishmentDetailsList(List<ReplenishmentDetails> replenishmentDetailsList) {
		this.replenishmentDetailsList = replenishmentDetailsList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDemandId() {
		return demandId;
	}
	public void setDemandId(int demandId) {
		this.demandId = demandId;
	}
	public String getDemandDate() {
		return demandDate;
	}
	public void setDemandDate(String demandDate) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
	public int getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		return "ReplenishmentTasks [id=" + id + ", demandId=" + demandId + ", demandDate=" + demandDate + ", state="
				+ state + ", operatorId=" + operatorId + ", operator=" + operator + "]";
	}
	
	
}
