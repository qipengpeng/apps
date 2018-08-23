/**
 * 
 */
package com.ugo.web;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ugo.dto.VmHttpRequest;
import com.ugo.service.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.entity.Orders;
import com.ugo.entity.Vendors;
import com.ugo.tools.ScheduledTime;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *
 */
@Controller
public class TimedTaskController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TimedTaskController.class);
	
	@Autowired 
	private TimedTaskService timedTaskService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private SalesService salesService;
	
	@Autowired
	private VendorsService vendorsService;

	@Autowired
	private VmReportService vmReportService;

	/**
	   * 每天22点30启动任务
	   * [秒] [分] [小时] [日] [月] [周] [年] 
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
	
	  @Scheduled(cron = ScheduledTime.pay_state)//隔行一次 
	  public synchronized void orderState()
	  {
		 logger.info("定时任务start");
	     List<Orders> orderList = timedTaskService.queryOrderList();
	     if(orderList.size()>0) {
	    	 for(Orders order : orderList) {
	    		Orders orders = new Orders();
	    		orders.setId(order.getId());
	    		orders.setPayStatus(3);
	    		logger.info("定时任务-修改订单取消------订单ID:"+order.getId());
	    		ordersService.updateOrderState(orders);
	 			logger.info("定时任务-恢复库存------库存ID:"+order.getSalesId());
	 			salesService.addStock(order.getSalesId());
	 			logger.info("定时任务-恢复库存----完成!");
	    	 }
	     }else {
	    	 logger.info("定时任务end");
		}
	     
	  } 
	  
	  /**
	   * @author qipeng 2018/8/16
	   * 定时关闭设备
	   * */
	  @Scheduled(cron=ScheduledTime.node_OFF)//隔行一次 
	  public void nodeListOFF(){
		 logger.info("定时任务start--点位停售");
		 timedTaskService.updateNodeStateList(2);
	  } 
	  
	  /**
	   * @author qipeng 2018/8/16
	   * 定时开启设备
	   * */
	  @Scheduled(cron=ScheduledTime.node_NO)//隔行一次
	  public void nodeListNO(){
		  logger.info("定时任务start--点位开售");
		  timedTaskService.updateNodeStateList(1);
	  } 
	  
	  /**
	   * @author qipeng 2018/8/17
	   * 定时检查设备离线预警
	   * */
	  @Scheduled(cron = ScheduledTime.offline_timer)//隔行一次
	  public void vendorOfflineWarning() {
		  List<Integer> vendorList = timedTaskService.findOfflineVendorList(ScheduledTime.time);
		  logger.info("定时检查设备离线预警"+vendorList.size());
		  if(vendorList.size()==0) {
			  return;
		  }
		  VmsController vmsController = SpringTool.getBean(VmsController.class);
		  for(int vendorId : vendorList) {
			  Vendors vendors = vendorsService.getVendors(vendorId);
			  vmsController.sendMsg("设备ID:"+vendorId+"\n点位ID:"+vendors.getNodeId()+"\n点位名称:"+vendors.getNodeName()+"\n预警内容:离线超过"+ScheduledTime.time+"分钟");
		  }
	  }
	  /**
	   * @author qipeng 2018/8/20
	   * 定时获取token
	   * */
	  @Scheduled(cron = ScheduledTime.task_token)//隔行一次
	  public void getAccessToken(){
	  	  logger.info("定时获取企业微信token");
		  JSONObject sendGet = null;
		  try {
			  sendGet = VmHttpRequest.sendGet();
			  logger.info("sendGet"+sendGet);
		  } catch (IOException e) {
			  logger.error("获取预警token异常!"+e);
			  e.printStackTrace();
		  }
		  String accessToken1 = sendGet.getString("access_token");
		  vmReportService.updateAccessToken(accessToken1);
	  }
}
