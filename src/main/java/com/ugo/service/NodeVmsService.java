/**
 * 
 */
package com.ugo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.NodeVmsDao;
import com.ugo.entity.NodeVms;

/**
 * @author sunshangfeng
 *
 */
@Service
public class NodeVmsService {
	
	@Autowired
	private NodeVmsDao nodeVmsDao;
	
	public List<NodeVms> getNodeVmsList(int nodeID){
		List<NodeVms> list = nodeVmsDao.getNodeVmsList(nodeID);
		return list;
	}
	@Transactional
	public void addNodeVms(NodeVms nodeVms) {
		nodeVmsDao.addNodeVms(nodeVms);
	}
	
}
