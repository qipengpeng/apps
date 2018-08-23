/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ugo.entity.ApkVersion;

/**
 * @author sunshangfeng
 *
 */
public interface ApkVersionDao {
	
	/**
	 * 保存上传apk数据
	 * */
	void addApk(ApkVersion apkVersion);
	
	/**
	 * 获取所有版本号
	 * */
	List<Integer>findVersionCode();
	
	/**
	 * 更新指定设备版本
	 * */
	void updateVersion(@Param("vendorId") String vendorId,@Param("version") int version,@Param("versionTime")String versionTime);
	
	/**
	 * 更新所有设备版本
	 * */
	void updateALLVersion(@Param("version")int version,@Param("versionTime")String versionTime);
	
	/**
	 * 设置当前设备版本
	 * */
	void setVersion(@Param("vendorId")String vendorId,@Param("version")int version);
}
