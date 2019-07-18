/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.money.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.money.entity.MoneyCountOutVo;
import com.jeesite.modules.money.entity.MoneyStatisticsInVo;
import com.jeesite.modules.money.service.MoneyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试树表Controller
 * @author ThinkGem
 * @version 2018-04-22
 */
@Controller
@RequestMapping(value = "${adminPath}/money")
public class MoneyController extends BaseController {

	@Autowired
	private MoneyService moneyService;
	
	/**
	 * 路线列表
	 */
	@RequiresPermissions("money:view")
	@RequestMapping(value = {"statistics", ""})
	public String list(MoneyStatisticsInVo moneyStatisticsInVo, Model model) {
		model.addAttribute("moneyStatistics", moneyStatisticsInVo);
		return "modules/money/moneyStatistics";
	}

	@RequestMapping(value = "echartsData")
	@ResponseBody
	public MoneyCountOutVo echartsData(MoneyStatisticsInVo moneyStatisticsInVo) {

		MoneyCountOutVo moneyCountOutVo = moneyService.queryParams(moneyStatisticsInVo);

		return moneyCountOutVo;
	}

//	/**
//	 * 根据状态查询路线结果
//	 * @param searchLineInVo
//	 * @param model
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequiresPermissions("money:view")
//	@RequestMapping(value = "listData")
//	@ResponseBody
//	public Page<MoneyCountOutVo> listData(MoneyStatisticsInVo userStatisticsInVo, Model model, HttpServletRequest request, HttpServletResponse response) {
//
//
//		MoneyCountOutVo moneyCountOutVo = moneyService.queryParams(userStatisticsInVo);
//
//		Page<MoneyCountOutVo> page = new Page();
//		page.getList().add(moneyCountOutVo);
//
//		return page;
//	}

}