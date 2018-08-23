/**
 * 
 */
package com.ugo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.VmReportDao;
import com.ugo.entity.ApkVersion;
import com.ugo.entity.ReportLogs;
import com.ugo.entity.VmsProductDetail;

/**
 * @author sunshangfeng
 * 设备端
 */
@Service
public class VmReportService {
	@Autowired
	private VmReportDao vmReportDao;
	
	@Autowired
	private WeiXinPayService weiXinPayService;
	/**
	 * 设备故障信息记录
	 * */
	@Transactional
	public void addReportLogs(ReportLogs reportLogs) {
		System.out.println("插入故障信息service------start");
		vmReportDao.addReportLogs(reportLogs);
		System.out.println("插入故障信息service------success");
	}
	
	/**
	 * 获取企业微信token
	 * */
	public String getAccessToken() {
		String accessToken = vmReportDao.getAccessToken();
		return accessToken;
	}
	
	/**
	 * 修改企业微信token
	 * */
	@Transactional
	public void updateAccessToken(String token) {
		vmReportDao.updateAccessToken(token);
	}
	
	/**
	 * 设备端商品详情
	 * */
	public Map<String,Object> queryVmsProductDetail(HttpServletRequest request){
		String vmCode = request.getParameter("vmCode");
		String url = weiXinPayService.getUrl(request);
		List<VmsProductDetail> productDetail = vmReportDao.queryVmsProductDetail(Integer.parseInt(vmCode));
		if(productDetail.size()<1) {
			return null;
		}
		Map<String,Object> mapList = new HashMap<>();
		List<Map<String,Object>> upList = new ArrayList<>();
		List<Map<String,Object>> downList = new ArrayList<>();
		String up = "上取餐口";
		String down = "下取餐口";
		for(VmsProductDetail detail : productDetail) {
			Map<String,Object> productMap = new HashMap<String,Object>();
			productMap.put("img", url+detail.getListImg());
			productMap.put("product_id", detail.getProductId());
			productMap.put("name", detail.getProductName());
			productMap.put("discount_price", weiXinPayService.rounding(detail.getNodeId(), detail.getSalePrice()));
			productMap.put("sale_price", detail.getSalePrice());
			productMap.put("desc", detail.getDescription());
			productMap.put("fit_people", detail.getFitPeople());
			productMap.put("netweight", detail.getNetweight());
			productMap.put("hot_time", detail.getHeatUpTime());
			if(detail.getChannelsType()==2 && detail.getChannelId()<3) {
				productMap.put("channel_id", detail.getChannelId());
				productMap.put("channel_name", up);
				upList.add(productMap);
			}
			if(detail.getChannelsType()==2 && detail.getChannelId()>2) {
				productMap.put("channel_id", detail.getChannelId()+1);
				productMap.put("channel_name", down);
				downList.add(productMap);
			}
		}
		mapList.put("up", upList);
		mapList.put("down", downList);
		return mapList;
	}
	
	/**
	 * 版本更新检测
	 * */
	public Map<String,Object> getApkVersion(int vendorId) {
		Map<String,Object> map = new HashMap<>();
		ApkVersion apkVersion = vmReportDao.getApkVersion(vendorId);
		if(apkVersion == null) {
			return null;
		}
		map.put("md5", apkVersion.getMd5());
		map.put("url", apkVersion.getDownUrl());
		map.put("verCode", apkVersion.getVersionCode());
		map.put("package", apkVersion.getPackageName());
		map.put("update_time", apkVersion.getUpdateTime());
		return map;
	}
}
