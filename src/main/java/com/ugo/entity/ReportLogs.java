/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 * 设备故障记录
 */
public class ReportLogs {
	private int id;
	private String vmCode;//设备ID
	private int errNum;//故障数量
	private String errDes;//故障描述
	private String createdAt;//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVmCode() {
		return vmCode;
	}
	public void setVmCode(String vmCode) {
		this.vmCode = vmCode;
	}
	public int getErrNum() {
		return errNum;
	}
	public void setErrNum(int errNum) {
		this.errNum = errNum;
	}
	public String getErrDes() {
		return errDes;
	}
	public void setErrDes(String errDes) {
		this.errDes = errDes;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "ReportLogs [id=" + id + ", vmCode=" + vmCode + ", errNum=" + errNum + ", errDes=" + errDes
				+ ", createdAt=" + createdAt + "]";
	}
	
}
