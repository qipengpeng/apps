/**
 * 
 */
package com.ugo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.VendorsDao;
import com.ugo.entity.MqttAcl;
import com.ugo.entity.MqttUser;
import com.ugo.entity.Vendors;

/**
 * @author sunshangfeng
 *
 */
@Service
public class VendorsService {
	
	@Autowired
	private VendorsDao vendorsDao;
	
	/**
	 * 获取设备列表
	 * */
	public List<Vendors> getList(){
		List<Vendors> list = vendorsDao.getList();
		for(Vendors  vendors:list) {
			int id = vendors.getId();
			Vendors vendorsNode = vendorsDao.getVendorsNode(id);
			if(vendorsNode !=null) {
			vendors.setNodeId(vendorsNode.getNodeId());
			vendors.setNodeName(vendorsNode.getNodeName());
			vendors.setNodeState(vendorsNode.getNodeState());
			}
		}
		return list;
	}
	
	/**
	 * 获取设备详情
	 * */
	public Vendors getVendors(int id) {
		Vendors vendors = vendorsDao.getVendors(id);
		Vendors vendorsNode = vendorsDao.getVendorsNode(id);
		if(vendorsNode !=null) {
			vendors.setNodeId(vendorsNode.getNodeId());
			vendors.setNodeName(vendorsNode.getNodeName());
			vendors.setNodeState(vendorsNode.getNodeState());
		}
		return vendors;
	}
	
	/**
	 * 查询设备对应的点位状态
	 * */
	public int getVendorsNodeState(int id) {
		Vendors vendors = vendorsDao.getVendorsNode(id);
		int state = vendors.getNodeState();
		return state;
	}
	
	/**
	 * 新增设备及mqtt
	 * */
	@Transactional
	public void addVendors(Vendors vendors) {
		//添加设备
		vendorsDao.addVendors(vendors);
		String username = vendors.getId()+"";
		
		MqttUser mqttUser = new MqttUser();
		mqttUser.setUsername(username);
		mqttUser.setPassword(vendors.getPassword());
		vendorsDao.addMqUser(mqttUser);
		
		MqttAcl mqttAcl = new MqttAcl();
		mqttAcl.setUsername(username);
		mqttAcl.setClientid(username);
		mqttAcl.setTopic("server/"+username);
		vendorsDao.addMqAcl(mqttAcl);
	}
	
	/**
	 * 查询设备状态
	 * */
	public int getVendorState(int id) {
		int vendorState = vendorsDao.getVendorState(id);
		return vendorState;
	}
	
	/**
	 * 修改设备状态
	 * */
	@Transactional
	public void updateVendorState(@Param("id")int id,@Param("state")int state) {
		vendorsDao.updateVendorState(id, state);
	}
	
	/**
	 * 修改点位设备状态
	 * */
	@Transactional
	public void updateNodeVmsState(int vendorId,int state) {
		vendorsDao.updateNodeVmsState(vendorId, state);
	}
	
	/**
	 * 设备端匹配设备详情
	 * */
	public Vendors findVendorsByIdAndPwd(int id,String password) {
		Vendors vendors = vendorsDao.findVendorsByIdAndPwd(id, password);
		return vendors;
	}
}
