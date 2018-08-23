/**
 * 
 */
package com.ugo.dto;

import java.util.List;
import java.util.Map;

/**
 * @author sunshangfeng
 *
 */
public class ListConstant {
	private String code;//返回码
	private String msg ;//
	private List<Map<String, Object>> data;//返回数据
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	
}
