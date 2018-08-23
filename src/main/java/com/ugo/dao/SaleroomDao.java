/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ugo.entity.Nodes;
import com.ugo.entity.Saleroom;

/**
 * @author sunshangfeng
 * 销售数据查看
 */
public interface SaleroomDao {
	
	/**
	 * 获取点位信息
	 * */
	List<Nodes> queryNodeList();
	
	/**
	 * 获取销售数据
	 * */
	List<Saleroom> querySaleroom(@Param("begin")String begin,@Param("end")String end,@Param("nodeId")List<Integer> nodeId);
}
