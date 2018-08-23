/**
 * 
 */
package com.ugo.web;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ugo.dao.ReplenishmentLogsDao;
import com.ugo.entity.ReplenishmentLogDetails;
import com.ugo.entity.ReplenishmentLogs;
import com.ugo.service.ReplenishmentLogsService;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *
 */
@Controller
@RequestMapping("/PC")
public class ReplenishmentLogsController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ReplenishmentLogsController.class);
	@Autowired
	private ReplenishmentLogsService repService;
	@Autowired
	private ReplenishmentLogsDao replenishmentLogsDao;
	
	/**
	 * 获取补货记录列表
	 * */
	@RequestMapping("/getReplenishmentLogsList")
	public String getRepList(Model model) {
		List<ReplenishmentLogs> repList = repService.getRepList();
		model.addAttribute("repList",repList);
		logger.debug("查询补货记录......");
		return "/replenishmentLogs";
	}
	
	/**
	 * 获取补货记录详情
	 * */
	@RequestMapping("/getReplenishmentLogDetails")
	public String getRepDetails(Model model,int id) {
		ReplenishmentLogs replenishmentLogs = replenishmentLogsDao.getReplenishmentLogs(id);
		List<ReplenishmentLogDetails> replenishmentLogDetails = replenishmentLogsDao.getReplenishmentLogDetails(id);
		replenishmentLogs.setReplenishmentLogDetailsList(replenishmentLogDetails);
		
		model.addAttribute("replenishmentLogs",replenishmentLogs);
		return "/replenishmentLogDetails";
	}
}
