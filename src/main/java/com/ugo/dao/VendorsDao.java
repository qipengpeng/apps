/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ugo.entity.MqttAcl;
import com.ugo.entity.MqttUser;
import com.ugo.entity.Vendors;

/**
 * @author sunshangfeng
 *设备Dao
 */
public interface VendorsDao {
	
		/**
		 * 获取设备信息
		 * */
		List<Vendors> getList();
		
		/**
		 * 新增设备
		 * */
		void addVendors(Vendors vendors);
		
		/**
		 * 获取设备
		 * */
		Vendors getVendors(int id);
		
	    /**
		 * 查询未投放的设备
		 * */
		List<Vendors> getNotSetList();
		
		 /**
		 * 查询设备对应的点位
		 * */
		Vendors getVendorsNode(int id);
		
		/**
		 * mqtt_user
		 * */
		void addMqUser(MqttUser mqttUser);
		/**
		 * mqtt_acl
		 * */
		void addMqAcl(MqttAcl mqttAcl);
		
		/**
		 * 查询设备状态
		 * */
		int getVendorState(int id);
			
		/**
		 * 修改设备状态
		 * */
		void updateVendorState(@Param("id")int id,@Param("state")int state);
		
		/**
		 * 修改点位设备状态
		 * */
		void updateNodeVmsState(@Param("id")int id,@Param("state")int state);
		
		/**
		 * 设备端匹配设备详情
		 * */
		Vendors findVendorsByIdAndPwd(@Param("id")int id,@Param("password")String password);
		
}
