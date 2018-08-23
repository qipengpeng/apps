/**
 * 
 */
package com.ugo.service;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ugo.entity.Page;
import com.ugo.entity.order.OrderDTO;
import com.ugo.tools.ExcelUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.OrdersDao;
import com.ugo.dao.SalesDao;
import com.ugo.dao.UsersDao;
import com.ugo.entity.Orders;
import com.ugo.entity.Sales;

import javax.servlet.http.HttpServletResponse;


/**
 * @author sunshangfeng
 *
 */
@Service
public class OrdersService {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(OrdersService.class);
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private SalesDao salesDao;

	private List<Orders> ordersList = null;  //订单查询集合

	private List<Orders> ordersDateList = null;  //订单根据时间查询集合
	
	/**
	 * 订单列表
	 * */
	public List<Orders> getOrderList(@Param("begin") int begin,@Param("end") int end){
		List<Orders> orderList = ordersDao.getOrderList(begin, end);
		return orderList;
	}
	
	/**
	 * 订单列表总条数
	 * */
	public long findOrderCount() {
		long count = ordersDao.findOrderCount();
		return count;
	}
	
	
	/**
	 * 生成订单
	 * */
	public List<Sales> getSales(Sales sales) {
		List<Sales> salesList = ordersDao.getSales(sales);
		return salesList;
	}
	
	/**
	 * 保存订单
	 * */
	@Transactional
	public void addOrders(Orders orders){
		ordersDao.addOrders(orders);
	}
	@Transactional
	public void addOrdersDetails(Orders orders){
		ordersDao.addOrdersDetails(orders);
	}
	
	/**
	 * 保存订单
	 * */
	@Transactional
	public synchronized int saveOrders(Orders orders,String openId,Long timeStamp) {
		
		/*
		 * 订单生成前,先查询是否有未支付的订单,若有,则更新订单.
		 * */
		int orderStatus = ordersDao.getOrderStatus(openId);
		/*if(orderStatus>0) {
			logger.info("该用户存在未付款订单!");
			Orders orders1 = ordersDao.getOrderId(openId);
			orders1.setPayStatus(3);
			ordersDao.updateOrder(orders1);
			logger.info("生成订单前--订单回退/恢复库存!");
			salesDao.addStock(orders1.getSalesId());
		}*/
		if(orderStatus==0) {
			logger.info("用户第一次下单,库存减少1!");
			salesDao.subStock(orders.getSalesId());
		}else {
			logger.info("该用户存在未付款订单!");
			Orders orders1 = ordersDao.getOrderId(openId);
			orders1.setPayStatus(3);
			ordersDao.updateOrder(orders1);
			logger.info("用户重复下单,库存不做改变!");
		}
		int user = usersDao.getUser(openId);
		if(user != 0) {
			int userId = usersDao.getUserId(openId);
			orders.setUserId(userId);
		}
		orders.setOpenId(openId);
		orders.setPayTime(timeStamp);
		logger.info("保存订单信息");
		ordersDao.addOrders(orders);
		if(orders.getId() !=0) {
			ordersDao.addOrdersDetails(orders);
			logger.info("生成订单"+orders.getId()+"库存减少!");
		}
		return orders.getId();
	}
	
	/**
	 * 订单取消,更新订单状态
	 * */
	@Transactional
	public int updateOrderAndSalesNum(Orders orders) {
		logger.info("手动取消订单,恢复库存------------订单ID:"+orders.getId());
		Orders orders1 = ordersDao.queryOrders(orders.getId());
		if(orders1.getPayStatus() == 1) {
			salesDao.addStock(orders1.getSalesId());
			logger.info("手动取消订单,恢复库存完成------------");
		}
		//取消订单
		logger.info("手动取消订单,修改订单状态------------");
		int updateNum = ordersDao.updateOrder(orders);
		
		return updateNum;
	}
	
	/**
	 * 支付完成更新订单状态
	 * */
	@Transactional
	public void updateOrderState(Orders orders) {
		logger.info("修改订单状态------------");
		ordersDao.updateOrder(orders);
		logger.info("修改订单状态------------");
	}
	/**
	 * 获取指定订单信息
	 * */
	public Orders queryOrders(int id) {
		logger.info("查询订单信息------------"+id);
		Orders orders = ordersDao.queryOrders(id);
		return orders;
	}
	
	/**
	 * 实时查询商品数量
	 * */
	public Orders querySales(String nodeId,String productId) {
		Sales sales = new Sales();
		Orders orders = new Orders();
		sales.setNodeId(Integer.parseInt(nodeId));
		sales.setProductId(Integer.parseInt(productId));
		//查询库存
		System.out.println("生成订单前查询库存,点位:"+nodeId+"--"+productId);
		List<Sales> salesList = getSales(sales);
		if(salesList.size()>0) {
			for(Sales sales1:salesList) {
				//获取可售商品
				if(sales1.getStatus()==1) {					
					orders.setSalesId(sales1.getId());
					orders.setNodeId(sales1.getNodeId());
					orders.setNodeName(sales1.getNodeName());
					orders.setProductId(sales1.getProductId());
					orders.setProductName(sales1.getProductName());
					orders.setVendorId(sales1.getVendorId());
					orders.setTotalPrice(sales1.getSalePrice());
					orders.setChannelId(sales1.getChannelId());
					orders.setChannelsType(sales1.getChannelsType());
					orders.setReturnCode(200);
					return orders ;
				}
			}
			for(Sales sales2:salesList) {
				//获取售中商品
				if(sales2.getStatus()==3) {					
					orders.setReturnCode(203);
					return orders ;
				}
			}
		}else {
			logger.error("未获取有效销售商品----------");
			orders.setReturnCode(201);
			return orders;
		}
		//设置订单状态码   201 售空      200可以销售  203 货道占用中 204 所以设备故障
		orders.setReturnCode(204);
		return orders;
	}
	
	public Orders salesToOrders(Sales sales) {
		Orders orders = new Orders();
		orders.setSalesId(sales.getId());
		orders.setNodeId(sales.getNodeId());
		orders.setNodeName(sales.getNodeName());
		orders.setProductId(sales.getProductId());
		orders.setProductName(sales.getProductName());
		orders.setVendorId(sales.getVendorId());
		orders.setTotalPrice(sales.getSalePrice());
		orders.setChannelId(sales.getChannelId());
		orders.setChannelsType(sales.getChannelsType());
		return orders;
	}
	
	/**
	 * 修改订单支付状态
	 * @param orders
	 * @author qipeng 2018/8/8
	 * */
	public void updateOrder(Orders orders) {
		ordersDao.updateOrder(orders);
	}
	
	/**
	 * 获取指定订单支付方式
	 * */
	public int findPayMethod(int id) {
		int payMethod = ordersDao.findPayMethod(id);
		return payMethod;
	}

	/**
	 * 订单查询的策略
	 * @param orderDTO
	 * @return
	 */
	public Map<String,Object> findOrders(OrderDTO orderDTO, String pageNow){
		if (orderDTO.getStartDate()!=null&&orderDTO.getEndDate()!=null&&orderDTO.getStartDate()!=""&&orderDTO.getEndDate()!=""){

			//将格式改为精确时间，修复无法查当天订单的问题
			orderDTO.setStartDate(orderDTO.getStartDate()+" 00:00:01");
			orderDTO.setEndDate(orderDTO.getEndDate()+" 23:59:59");
			System.out.println(orderDTO);
			ordersDateList =  ordersDao.getOrdersByDate(toOrders(orderDTO),orderDTO.getStartDate(),orderDTO.getEndDate());
			getPage(pageNow,ordersDateList);
			return getOrdersAndPage(ordersDateList,getPage(pageNow,ordersDateList),1);
		}else{

			ordersList = ordersDao.getOrders2(toOrders(orderDTO));

			getPage(pageNow,ordersList);
			return getOrdersAndPage(ordersList,getPage(pageNow,ordersList),2);
		}
	}

	/**
	 * 计算页数
	 * @param pageNow
	 * @param list
	 * @return
	 */
	private Page getPage(String pageNow, List<Orders> list){
		//获取当前页数

		//获取总页数
		int totalCount=list.size();
		Page page=null;

		if (pageNow!=null) {
			page=new Page(Integer.parseInt(pageNow), totalCount);
		}else {
			page=new Page(1, totalCount);
		}
		return page;
	}

	/**
	 * 计算出每页的内容
	 * @param list
	 * @param page
	 * @param findType 查找类型：时间查找“1” 非时间查找“2”
	 * @return
	 */
	private Map<String,Object> getOrdersAndPage(List<Orders> list, Page page,int findType){
		page.setScope("findPage");
		List<Orders> list1;
		Map<String,Object> map = new HashMap<>();
		int start = page.getPageNow()*page.getPageSize()-page.getPageSize();
		int end = page.getPageNow()*page.getPageSize();
		if (end>list.size()){
			end = list.size();
		}
		list1 = list.subList(start,end);
		map.put("list",list1);
		map.put("page",page);
		map.put("findType",findType);
		return map;
	}


	/**
	 * 查询分页跳转
	 * @param pageNow 当前页数
	 * @param findType 查找类型：时间查找“1” 非时间查找“2”
	 * @return
	 */
	public Map<String,Object> findPage(String pageNow,String findType){
		if (("1").equals(findType)){
			return getOrdersAndPage(ordersDateList,getPage(pageNow,ordersDateList),1);
		}else{
			return getOrdersAndPage(ordersList,getPage(pageNow,ordersList),2);
		}
	}


	/**
	 * 将orderDTO转为orders
	 * @param orderDTO 前端传输数据类
	 * @return orders
	 */
	private Orders toOrders(OrderDTO orderDTO){
		Orders orders = new Orders();
		orders.setNodeId(orderDTO.getNodeId());
		orders.setNodeName(orderDTO.getNodeName());
		orders.setProductId(orderDTO.getProductId());
		orders.setProductName(orderDTO.getProductName());
		orders.setOrderStatus(orderDTO.getShippingState());
		orders.setId(orderDTO.getId());
		orders.setPayStatus(orderDTO.getPayStatus());
		return orders;
	}


	/**
	 * Excel导出方法
	 * @param findType 查询方式
	 * @param response
	 */
	public void exportExcel(String findType,HttpServletResponse response){
		String[] title = {"ID","用户ID","点位名称","设备ID","设备类型","商品名称","实际价格","支付价格","出货状态","支付状态","支付方式","订单时间"};
		String titleName = "订单表";
		String fileName = titleName+System.currentTimeMillis()+".xls";
		if (("1").equals(findType)){
			HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(titleName,title,toContent(ordersDateList,title.length),null);
			setResponseHeader(response,fileName,wb);
		}
		if (("2").equals(findType)){
			HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(titleName,title,toContent(ordersList,title.length),null);
			setResponseHeader(response,fileName,wb);
		}

		if (("3").equals(findType)){
			HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(titleName,title,toContent(ordersDao.getOrdersByMonth(new Date()),title.length),null);
			setResponseHeader(response,fileName,wb);
		}


	}



	//发送响应流方法
	public void setResponseHeader(HttpServletResponse response, String fileName, HSSFWorkbook wb) {
		try {
			try {
				fileName = new String(fileName.getBytes(),"ISO8859-1");
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		//响应到客户端
		try {
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String[][] toContent(List<Orders> ordersList,int titbleLength ){
		String[][] content =new String[ordersList.size()][];
		for (int i = 0; i <ordersList.size() ;i++) {
			String[] order = new String[titbleLength];
			order[0] = Integer.toString( ordersList.get(i).getId());
			order[1] = Integer.toString(ordersList.get(i).getUserId());
			order[2] = ordersList.get(i).getNodeName();
			order[3] = Integer.toString(ordersList.get(i).getVendorId());
			order[4] = ordersList.get(i).getChannelsTypeTransition();
			order[5] = ordersList.get(i).getProductName();
			order[6] = ordersList.get(i).getTotalPriceTransition();
			order[7] = ordersList.get(i).getPayPriceTransition();
			order[8] = ordersList.get(i).getOrderStatusTransition();
			order[9] = ordersList.get(i).getPayStatusTransition();
			order[10] = ordersList.get(i).getPayMethodTransition();
			order[11] = Long.toString(ordersList.get(i).getPayTime());
			content[i] = order;
		}
		return content;
	}
}
