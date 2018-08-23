package com.ugo.web;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.LoggerFactory;

import com.ugo.bus.BusListener;
import com.ugo.bus.MsgBus;
import com.ugo.entity.Orders;
import com.ugo.entity.Vendors;
import com.ugo.service.OrdersService;
import com.ugo.service.SalesService;
import com.ugo.service.VendorsService;

import ch.qos.logback.classic.Logger;

public class AppContextListener implements ServletContextListener,BusListener{
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(AppContextListener.class); 
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		MsgBus.destory();
		System.out.println("contextDestroyed");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("contextInitialized");
		MsgBus.init(this);
	}
	
	@Override
	public void online(String deviceId) {
		// TODO Auto-generated method stub
		System.out.println("deviceId--------"+deviceId);
		if("bus".equals(deviceId)) {
			logger.info("启动设备终端online...........bus");
			/*VmsController vmsController = SpringTool.getBean(VmsController.class);
			try {
				vmsController.sendMsg("启动设备终端bus");
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				logger.info("企业微信消息发送异常");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.info("企业微信消息发送异常");
				e.printStackTrace();
			}*/
		}else {
			int id = Integer.parseInt(deviceId);
			VendorsService vendorsService = SpringTool.getBean(VendorsService.class);
			int vendorState = vendorsService.getVendorState(id);
			if(vendorState != 2) {
				vendorsService.updateVendorState(id, 2);
				logger.info("启动设备终端online..........."+deviceId);
			}
		}
	}

	@Override
	public void offline(String deviceId) {
		// TODO Auto-generated method stub
		if("bus".equals(deviceId)) {
			logger.info("关闭设备终端offline...........bus");
		}else {
			int id = Integer.parseInt(deviceId);
			VendorsService vendorsService = SpringTool.getBean(VendorsService.class);
			int vendorState = vendorsService.getVendorState(id);
			if(vendorState != 1) {
				vendorsService.updateVendorState(id, 1);
				logger.info("关闭设备终端offline..........."+deviceId);
			}
		}
	}

	@Override
	public void lastWill(String deviceId) {
		// TODO Auto-generated method stub
		if("bus".equals(deviceId)) {
			logger.info("设备终端超时离线lastWill...........bus");
		}else {
			int id = Integer.parseInt(deviceId);
			
			VendorsService vendorsService = SpringTool.getBean(VendorsService.class);
			int vendorState = vendorsService.getVendorState(id);
			if(vendorState != 1) {
				vendorsService.updateVendorState(id, 1);
				logger.info("设备终端超时离线lastWill..........."+deviceId);
			}
		}
	}

	@Override
	public void receivedReplyDeliverMsg(String msgId,String deivceId, int columnNo, int status, String statusMsg) {
		// TODO Auto-generated method stub
			OrdersService ordersService = SpringTool.getBean(OrdersService.class);
			SalesService salesService = SpringTool.getBean(SalesService.class);
			WXRefundController refund = SpringTool.getBean(WXRefundController.class);
			VmsController vmsController = SpringTool.getBean(VmsController.class);
			Orders orders = new Orders();
			logger.info("设备启动前消息---------"+msgId);
			Orders orders2 = ordersService.queryOrders(Integer.parseInt(msgId));
			orders.setId(Integer.parseInt(msgId));
			if(status ==2) {
				//出货状态设定 1 未出货 2 出货成功 3 出货失败 4超时取消
				orders.setOrderStatus(3);
				logger.error("出货前更新出货状态.........出货失败!"+msgId);
				ordersService.updateOrderState(orders);
				logger.error("锁定库存状态.........");
				salesService.updateSalesState(Integer.parseInt(deivceId), columnNo, 4);
				logger.error("恢复库存数量.........");
				salesService.addStock(orders2.getSalesId());
		        try {
					refund.autoRefund(Integer.parseInt(msgId), 5);
				} catch (NumberFormatException e) {
					logger.error("退款入参异常-------------");
				} catch (Exception e) {
					logger.error("退款失败-------------");
				}
		        vmsController.sendMsg("设备ID:"+deivceId+"\n货道编号:"+columnNo+"\n点位ID:"+orders2.getNodeId()+"\n点位名称:"+orders2.getNodeName()+"\n出货失败:"+statusMsg);
			}else if(status ==3) {
				//出货状态设定 1 未出货 2 出货成功 3 出货失败 4超时取消
				logger.error("出货前设备报告.........超时!"+msgId);
				orders.setOrderStatus(4);
				ordersService.updateOrderState(orders);
				logger.error("锁定库存状态.........");
				salesService.updateSalesState(Integer.parseInt(deivceId), columnNo, 1);
				logger.error("恢复库存数量.........");
				salesService.addStock(orders2.getSalesId());
		        try {
					refund.autoRefund(Integer.parseInt(msgId), 5);
				} catch (NumberFormatException e) {
					logger.error("退款入参异常-------------");
				} catch (Exception e) {
					logger.error("退款失败-------------");
				}
		        vmsController.sendMsg("设备ID:"+deivceId+"\n货道编号:"+columnNo+"\n点位ID:"+orders2.getNodeId()+"\n点位名称:"+orders2.getNodeName()+"\n出货失败:"+statusMsg);
		        
			}else if(status ==1){
				logger.error("出货前更新出货状态.........设备占用5!"+msgId);
				orders.setOrderStatus(5);
				ordersService.updateOrderState(orders);
				salesService.addStock(orders2.getSalesId());
		        try {
		        	refund.autoRefund(Integer.parseInt(msgId), 5);
				} catch (NumberFormatException e) {
					logger.error("退款入参异常-------------");
				} catch (Exception e) {
					logger.error("退款失败-------------");
				}
		        vmsController.sendMsg("设备ID:"+deivceId+"\n货道编号:"+columnNo+"\n点位ID:"+orders2.getNodeId()+"\n点位名称:"+orders2.getNodeName()+"\n出货失败:"+statusMsg);
			}
			
	}

	@Override
	public void receivedDeliverLogMsg(String msgId,String deviceId, int columnNo, int status, String statusMsg) {
		// TODO Auto-generated method stub
		OrdersService ordersService = SpringTool.getBean(OrdersService.class);
		SalesService salesService = SpringTool.getBean(SalesService.class);
		WXRefundController refund = SpringTool.getBean(WXRefundController.class);
		VmsController vmsController = SpringTool.getBean(VmsController.class);
		//获取订单数据
		Orders orders2 = ordersService.queryOrders(Integer.parseInt(msgId));
		Orders orders = new Orders();
		if(msgId == null ||msgId.isEmpty()) {
			logger.error("设备返回数据异常,订单数据丢失！！！！！"+msgId);
			vmsController.sendMsg("设备ID:"+deviceId+"\n货道编号:"+columnNo+"\n点位ID:"+orders2.getNodeId()+"\n点位名称:"+orders2.getNodeName()+"\n故障原因:"+statusMsg);
			return;
		}
		System.out.println("订单ID---------------"+msgId);
		orders.setId(Integer.parseInt(msgId));
		logger.info("设备返回状态........."+status+"订单ID："+msgId);
		if(status ==0) {
			logger.info("收到设备出货成功出货后结果---！");
			orders.setOrderStatus(2);
			logger.info("更新订单状态......");
			ordersService.updateOrderState(orders);
			int channlesType = salesService.getChannlesType(Integer.parseInt(msgId));
			if(channlesType == 2) {
				logger.info("恢复库存状态.........");
				//库存状态设定 1 可执行 3 占用中 4 设备故障
				salesService.updateSalesState(Integer.parseInt(deviceId), columnNo, 1);
			}
		}else if(status ==2){
			logger.error("收到设备故障的出货后结果-恢复库存/自动退款-------------"+msgId);
			orders.setOrderStatus(3);
			//库存状态设定 1 可执行 3 占用中 4 设备故障
			logger.info("更新订单状态......");
			ordersService.updateOrderState(orders);
			logger.info("锁定库存状态.........");
			salesService.updateSalesState(Integer.parseInt(deviceId), columnNo, 4);
			if("00无".equals(statusMsg)) {
				logger.info("设备故障,无设备状态/不作自动退款处理!"+msgId);
			}else {
				try {
					refund.autoRefund(Integer.parseInt(msgId), 5);
				} catch (NumberFormatException e) {
					logger.error("退款入参异常-------------失败!");
				} catch (Exception e) {
					logger.error("退款失败-------------");
				}
				logger.error("收到设备故障的出货后结果-恢复库存/自动退款----------退款结束！"+msgId);
			}
	        vmsController.sendMsg("设备ID:"+deviceId+"\n货道编号:"+columnNo+"\n点位ID:"+orders2.getNodeId()+"\n点位名称:"+orders2.getNodeName()+"\n出货失败:"+statusMsg);
		}else if(status ==3){
			logger.error("收到超时的出货后结果-不恢复库存/未自动退款-------------"+msgId);
			orders.setOrderStatus(4);
			//库存状态设定 1 可执行 3 占用中 4 设备故障
			salesService.updateSalesState(Integer.parseInt(deviceId), columnNo, 1);
//          salesService.addStock(orders2.getSalesId());
			ordersService.updateOrderState(orders);
//	        try {
//	        	refund.autoRefund(Integer.parseInt(msgId), 5);
//			} catch (NumberFormatException e) {
//				logger.error("退款入参异常-------------");
//			} catch (Exception e) {
//				logger.error("退款失败-------------");
//			}
			vmsController.sendMsg("设备ID:"+deviceId+"\n货道编号:"+columnNo+"\n点位ID:"+orders2.getNodeId()+"\n点位名称:"+orders2.getNodeName()+"\n故障原因:"+statusMsg);
		}
		logger.info("设备出货结束！");
	}

}
