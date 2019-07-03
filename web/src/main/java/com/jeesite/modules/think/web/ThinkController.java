/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.think.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.test.entity.TestData;
import com.jeesite.modules.test.service.TestDataService;
import com.jeesite.modules.think.entity.User;
import com.jeesite.modules.think.service.ThinkService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试数据Controller
 * @author ThinkGem
 * @version 2018-04-22
 */
@Controller
@RequestMapping(value = "${adminPath}/think")
public class ThinkController extends BaseController {

//	@Autowired
//	private TestDataService testDataService;
	@Autowired
	private ThinkService testDataService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TestData get(String id, boolean isNewRecord) {
		return testDataService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("test:testData:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, Model model) {
		model.addAttribute("user", user);
		return "modules/think/thinkList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("test:testData:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response) {
//		user.setPage(new Page<>(request, response));
		Page<User> page = new Page<>();

		User userData = new User();
		userData.setKid("1111111111111111111");
		userData.setUsername("think");
		userData.setPassword("8888888888888888");

		page.getList().add(userData);
		return page;
	}
//	public Page<TestData> listData(TestData testData, HttpServletRequest request, HttpServletResponse response) {
//		testData.setPage(new Page<>(request, response));
//		Page<TestData> page = testDataService.findPage(testData);
//		return page;
//	}
}