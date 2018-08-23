/**
 * 
 */
package com.ugo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.SaleroomDao;
import com.ugo.entity.Nodes;
import com.ugo.entity.Saleroom;

/**
 * @author sunshangfeng
 * 销售额数据业务逻辑
 */
@Service
@Transactional
public class SaleroomService {
	
	@Autowired
	private SaleroomDao saleroomDao;
	
	/**
	 * 获取点位信息
	 * */
	public List<Nodes> queryNodeList(){
		List<Nodes> nodeList = saleroomDao.queryNodeList();
		if(nodeList.size()>0) {
			return nodeList;
		}
		return null;
	}
	
	/**
	 * 获取销售数据
	 * */
	public List<Saleroom> querySaleroom(String begin,String end,List<Integer> nodeId){
		List<Saleroom> querySaleroom = saleroomDao.querySaleroom(begin, end, nodeId);
		if(querySaleroom.size()>0) {
			return querySaleroom;
		}
		return null;
		
	}
}
