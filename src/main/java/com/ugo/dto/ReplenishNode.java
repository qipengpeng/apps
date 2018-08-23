/**
 * 
 */
package com.ugo.dto;

import java.util.List;
import java.util.Map;

/**
 * @author sunshangfeng
 *补货点位
 */
public class ReplenishNode {
	private int error_code;//返回码
	private String task_id;//
	private List<Map<String, Object>> data;
	
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ReplenishNode [error_code=" + error_code + ", task_id=" + task_id + ", data=" + data + "]";
	}
	
}
