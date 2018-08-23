package com.ugo.entity;

import java.util.Map;

public class Login {
	private int error_code;//
	private Map<String,Object> data;//
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	
	
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Login [error_code=" + error_code + ", data=" + data + "]";
	}

	
}
