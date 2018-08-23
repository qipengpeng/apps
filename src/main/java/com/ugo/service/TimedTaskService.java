/**
 * 
 */
package com.ugo.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.OrdersDao;
import com.ugo.dao.TimedTaskDao;
import com.ugo.entity.Orders;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *	定时器任务
 *	qipeng
 *@version  V1.0
 */
@Service
@Transactional
public class TimedTaskService {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(OrdersService.class);
	@Autowired
	private TimedTaskDao timedTaskDao;
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private SalesService salesDao;
	
	/**
	   * 每天22点30启动任务
	   * 
	   * CRON表达式 含义 
		“0 0 12 * * ?” 每天中午十二点触发 
		“0 15 10 ? * *” 每天早上10：15触发 
		“0 15 10 * * ?” 每天早上10：15触发 
		“0 15 10 * * ? *” 每天早上10：15触发 
		“0 15 10 * * ? 2005” 2005年的每天早上10：15触发 
		“0 * 14 * * ?” 每天从下午2点开始到2点59分每分钟一次触发 
		“0 0/5 14 * * ?” 每天从下午2点开始到2：55分结束每5分钟一次触发 
		“0 0/5 14,18 * * ?” 每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 
		“0 0-5 14 * * ?” 每天14:00至14:05每分钟一次触发 
		“0 10,44 14 ? 3 WED” 三月的每周三的14：10和14：44触发 
		“0 15 10 ? * MON-FRI” 每个周一、周二、周三、周四、周五的10：15触发
		*
	   */
		
	
	  @Transactional
	  //@Scheduled(cron = "0 0/3 * * * ?")//每隔5秒隔行一次 
	  public void orderState()
	  {
		 logger.info("定时任务 开始执行--------"+System.currentTimeMillis()/1000);
	     List<Orders> orderList = timedTaskDao.queryOrderList();
	     if(orderList.size()>0) {
	    	 for(Orders order : orderList) {
	    		Orders orders = new Orders();
	    		orders.setId(order.getId());
	    		orders.setPayStatus(3);
	    		logger.info("定时任务-修改订单取消------订单ID:"+order.getId());
	 			ordersDao.updateOrder(orders);
	 			logger.info("定时任务-恢复库存------库存ID:"+order.getSalesId());
	 			salesDao.addStock(order.getSalesId());
	 			logger.info("定时任务-恢复库存----完成!");
	    	 }
	     }else {
	    	 logger.info("定时任务---取消订单0个!");
		}
	     
	  } 
	  
	  /**
		 * 获取超时订单
		 * */
	  public List<Orders> queryOrderList(){
		  List<Orders> orderList = timedTaskDao.queryOrderList();
		  return orderList;
	  }
	  
	   /**
		 * @author qipeng 2018/8/17
		 * 定时点位开关
		 * */
	  public void updateNodeStateList(int state) {
		  timedTaskDao.updateNodeStateList(state);
	  }
	  
	  /** 
	   * @author qipeng 2018/8/17
	   * 获取离线超时设备
	   * */
	  public List<Integer>findOfflineVendorList(int time){
		  List<Integer> offlineVendorList = timedTaskDao.findOfflineVendorList(time);
		  return offlineVendorList;
	  }
				
}
