/**
 * 
 */
package com.ugo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.ApkVersionDao;
import com.ugo.entity.ApkVersion;

/**
 * @author sunshangfeng
 *
 */
@Service
@Transactional
public class ApkVersionService {
	
	@Autowired
	private ApkVersionDao apkVersionDao;
	
	/**
	 * 保存上传apk数据
	 * */
	public boolean addApk(ApkVersion apkVersion) {
		try {
			apkVersionDao.addApk(apkVersion);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取所有版本号
	 * */
	public List<Integer>findVersionCode(){
		List<Integer> versionCode = apkVersionDao.findVersionCode();
		return versionCode;
	}
	
	/**
	 * 更新指定设备版本
	 * */
	public void updateVersion(String vendorId,int version,String versionTime) {
		apkVersionDao.updateVersion(vendorId, version, versionTime);
	}
	
	/**
	 * 更新所有设备版本
	 * */
	public void updateALLVersion(int version,String versionTime) {
		apkVersionDao.updateALLVersion(version,versionTime);
	}
	
	/**
	 * 更新所有设备版本
	 * */
	public void setVersion(String vmCode,int version) {
		apkVersionDao.setVersion(vmCode, version);
	}
}
