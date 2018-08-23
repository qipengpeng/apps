/**
 * 
 */
package com.ugo.dto;

import java.util.List;
import java.util.Map;

/**
 * @author sunshangfeng
 *接口通用类
 */
public class Constant {
		private int error_code;//返回码
		private String err_msg ;//
		private List<Map<String, Object>> data;//返回数据
		public int getError_code() {
			return error_code;
		}
		
		public String getErr_msg() {
			return err_msg;
		}

		public void setErr_msg(String err_msg) {
			this.err_msg = err_msg;
		}

		public void setError_code(int error_code) {
			this.error_code = error_code;
		}
		public List<Map<String, Object>> getData() {
			return data;
		}
		public void setData(List<Map<String, Object>> data) {
			this.data = data;
		}

		@Override
		public String toString() {
			return "Constant [error_code=" + error_code + ", err_msg=" + err_msg + ", data=" + data + "]";
		}
		
}
