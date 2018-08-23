/**
 * 
 */
package com.ugo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ugo.entity.order.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ugo.dao.OrdersDao;
import com.ugo.entity.Orders;
import com.ugo.entity.Page;
import com.ugo.service.OrdersService;



/**
 * @author sunshangfeng
 *设备
 */
@RequestMapping("/PC")
@Controller
public class OrdersController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(OrdersController.class);
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrdersDao ordersDao;
	/*
	 * 获取订单列表
	 * */
	@RequestMapping("/getOrdersList")
	public String getOrdersList(Model model,HttpServletRequest request) {		
		//获取当前页数
        String pageNow=request.getParameter("pageNow");
        //获取总页数
        int totalCount=(int)ordersService.findOrderCount();
        Page page=null;
        List<Orders> list=new ArrayList<Orders>();
        if (pageNow!=null) {
            page=new Page(Integer.parseInt(pageNow), totalCount);
            list=this.ordersService.getOrderList(page.getStartPos(),page.getPageSize());
        }else {
            page=new Page(1, totalCount);
            list=this.ordersService.getOrderList(page.getStartPos(),page.getPageSize());
        }

		page.setScope("getOrdersList");
        model.addAttribute("ordersList", list);
        model.addAttribute("page", page);
		model.addAttribute("findType", 3);
		
		return "/orders";
	}
	
	/*
	 * 获取订单详情
	 * */
	/*@RequestMapping("/findOrders")
	public String getOrders(HttpServletRequest request,Model model) {
		String nodeId = request.getParameter("nodeId");
		String nodeName = request.getParameter("nodeName");
		String payStatus = request.getParameter("payStatus");
		String id = request.getParameter("id");
		Orders orders = new Orders();
		if(id != "" && id != null) {
			orders.setId(Integer.parseInt(id));
		}
		if(nodeId != "" && nodeId != null) {
			orders.setNodeId(Integer.parseInt(nodeId));
				}
		if(payStatus != "" && payStatus != null) {
			orders.setPayStatus(Integer.parseInt(payStatus));
		}

		orders.setNodeName(nodeName);
		List<Orders> ordersList = ordersDao.getOrders(orders);
		model.addAttribute("ordersList",ordersList);
		return "/orders";
	}*/

	/**
	 *	订单查询控制器
	 * @param model
	 * @param orderDTO 前端数据传输类
	 * @return
	 */
	@PostMapping("/findOrders")
	public String findOrders(Model model, OrderDTO orderDTO, String pageNow){
		logger.info("查询条件为："+orderDTO);
		Map<String,Object> map = ordersService.findOrders(orderDTO,pageNow);
		model.addAttribute("ordersList",map.get("list"));
		model.addAttribute("page",map.get("page"));
		model.addAttribute("findType",map.get("findType"));
		return "/orders";
	}


	/**
	 * 查询页面分页
	 * @param model
	 * @param pageNow 当前页面
	 * @param findType 查找类型：时间查找“1” 非时间查找“2”
	 * @return
	 */
	@GetMapping("/findPage")
	public String findPage(Model model,String pageNow,String findType){
		Map<String,Object> map = ordersService.findPage(pageNow,findType);
		model.addAttribute("ordersList",map.get("list"));
		model.addAttribute("page",map.get("page"));
		model.addAttribute("findType",map.get("findType"));
		return "/orders";
	}

	@GetMapping("/exportExcel")
	public void exportExcel(HttpServletResponse response, String findType){
		ordersService.exportExcel(findType,response);
	}
}
