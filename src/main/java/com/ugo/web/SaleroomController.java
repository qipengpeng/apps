/**
 * 
 */
package com.ugo.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ugo.entity.Nodes;
import com.ugo.entity.Saleroom;
import com.ugo.service.SaleroomService;

/**
 * @author sunshangfeng
 *	销售额查看数据接口
 */
@Controller
@RequestMapping("/data")
public class SaleroomController {
	
	@Autowired
	private SaleroomService saleroomService;
	
	@RequestMapping("/queryNodes")
	public String queryNodeList(HttpServletRequest request) {
		List<Nodes> nodeList = saleroomService.queryNodeList();
		request.setAttribute("nodeList", nodeList);
		return "/qywx/saleroom";
	}
	
	@ResponseBody
	@RequestMapping("/ajax/cons_trend")
	public Map<String,Object> querySaleroom(String begin,String end,@RequestParam(value="nodeIds[]")String[] nodeIds) {
			Map<String,Object> map = new HashMap<>();
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
			String format = "";
				 try {
					format = fmt.format(fmt.parse("23:59:59"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			String endTime = end+" "+format;
			System.out.println(endTime);
			List<Integer> nodeId = new ArrayList<>();
			for(int i=0;i<nodeIds.length;i++) {
				nodeId.add(Integer.parseInt(nodeIds[i]));
			}
			System.out.println("点位集----------"+nodeId);
			List<Saleroom> querySaleroom = saleroomService.querySaleroom(begin, endTime, nodeId);
			map.put("code", 0);
			map.put("msg", "");
			map.put("data", querySaleroom);
			return map;
	}
}
