/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import com.ugo.entity.ApkVersion;
import com.ugo.entity.ReportLogs;
import com.ugo.entity.VmsProductDetail;

/**
 * @author sunshangfeng
 *	设备端
 */
public interface VmReportDao {
	
	/**
	 * 保存设备故障信息
	 * */
	void addReportLogs(ReportLogs reportLogs);
	
	/**
	 * 获取企业微信token
	 * */
	String getAccessToken();
	
	/**
	 * 修改企业微信token
	 * */
	void updateAccessToken(String token);
	
	/**
	 * 设备端商品详情
	 * */
	List<VmsProductDetail> queryVmsProductDetail(int vmCode);
	
	/**
	 * 版本更新检测
	 * */
	ApkVersion getApkVersion(int vendorId);
}
