/**
 * 
 */
package com.ugo.dto;

import java.util.List;
import java.util.Map;

/**
 * @author sunshangfeng
 *交货单
 */
public class DeliveryOrder {
	private int err_code;//返回码
	private int demand_code;//需求单号-交割单号
	private String demand_date;//
	private List<Map<String, Object>>products;
	
	public int getErr_code() {
		return err_code;
	}
	public void setErr_code(int err_code) {
		this.err_code = err_code;
	}
	
	public int getDemand_code() {
		return demand_code;
	}
	public void setDemand_code(int demand_code) {
		this.demand_code = demand_code;
	}
	public String getDemand_date() {
		return demand_date;
	}
	public void setDemand_date(String demand_date) {
		this.demand_date = demand_date;
	}
	public List<Map<String, Object>> getProducts() {
		return products;
	}
	public void setProducts(List<Map<String, Object>> products) {
		this.products = products;
	}
	@Override
	public String toString() {
		return "DeliveryOrder [err_code=" + err_code + ", demand_code=" + demand_code + ", demand_date=" + demand_date
				+ ", products=" + products + "]";
	}
	
}
