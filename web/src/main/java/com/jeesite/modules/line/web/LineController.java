/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.line.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.commom.utils.ApiPage;
import com.jeesite.modules.line.entity.*;
import com.jeesite.modules.line.service.LineService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 测试树表Controller
 * @author ThinkGem
 * @version 2018-04-22
 */
@Controller
@RequestMapping(value = "${adminPath}/line")
public class LineController extends BaseController {

	@Autowired
	private LineService lineService;
	
	/**
	 * 路线列表
	 */
	@RequiresPermissions("line:view")
	@RequestMapping(value = {"list", ""})
	public String list(SearchLineInVo line, Model model) {
		line.setPhone("9527");
		model.addAttribute("line", line);
		return "modules/line/lineList";
	}

	/**
	 * 待审核路线列表
	 */
	@RequiresPermissions("line:view")
	@RequestMapping(value = "audits")
	public String audits(SearchLineInVo line, Model model) {

		model.addAttribute("line", line);
		return "modules/line/auditLine";
	}

	/**
	 * 轮播图展示路线列表
	 */
	@RequiresPermissions("line:view")
	@RequestMapping(value = "lineHomeList")
	public String lineHomeList(SearchLineInVo line, Model model) {

		model.addAttribute("line", line);
		return "modules/line/lineHomeList";
	}

	private void page(HttpServletRequest request, SearchLineInVo searchLineInVo){
		String pageSize = request.getParameter("pageSize");
		String pageNo = request.getParameter("pageNo");
		if(!StringUtils.isEmpty(pageSize)){
			searchLineInVo.setPageSize(Integer.parseInt(pageSize));
		}
		if(!StringUtils.isEmpty(pageNo)){
			searchLineInVo.setPageNumber(Integer.parseInt(pageNo));
		}
	}

	/**
	 * 根据状态查询路线结果
	 * @param searchLineInVo
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("line:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<LineSearchOutVo> listData(SearchLineInVo searchLineInVo, Model model, HttpServletRequest request, HttpServletResponse response) {

		page(request, searchLineInVo);

		ApiPage<LineSearchOutVo> lineSearchOutVoApiPage = lineService.queryAll(searchLineInVo);

		Page<LineSearchOutVo> page = new Page();
		page.setList(lineSearchOutVoApiPage.getEntities());
		page.setCount(lineSearchOutVoApiPage.getCount());
		page.setPageNo(searchLineInVo.getPageNumber());
		page.setPageSize(searchLineInVo.getPageSize());

		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("line:view")
	@RequestMapping(value = "form")
	public String form(SearchLineInVo searchLineInVo, Model model, HttpServletRequest request) {



		return "modules/line/lineForm";
	}

	/**
	 * 保存数据
	 */
//	@RequiresPermissions("line:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SearchLineInVo line) {
		lineService.save(line);
		return renderResult(Global.TRUE, text("保存数据成功！"));
	}

	/**
	 * 删除路线
	 * @param empUser
	 * @return
	 */
	@RequiresPermissions("line:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String lineKid, Integer flag) {

		if (StringUtils.isEmpty(lineKid)){

			return renderResult(Global.TRUE, "操作失败,lineKid不能为空！");
		}

		String message = lineService.delete(lineKid, flag);
		return renderResult(Global.TRUE, message);
	}

	/**
	 * 路线上线,可批量
	 * @param empUser
	 * @return
	 */
	@RequiresPermissions("line:edit")
	@RequestMapping(value = "update")
	@ResponseBody
	public String update(String lineKid, Integer lineStatus, String remarks) {

		if (StringUtils.isEmpty(lineKid)){

			return renderResult(Global.FALSE, "操作失败,lineKid不能为空！");
		}

		AuditLinesInVo auditLinesInVo = new AuditLinesInVo();
		auditLinesInVo.setLineKids(lineKid);
		auditLinesInVo.setLineStatus(lineStatus);
		auditLinesInVo.setRemarks(remarks);

		lineService.update(auditLinesInVo);
		return renderResult(Global.TRUE, text("审核路线'{0}'成功", lineKid));
	}

	/**
	 * 路线首页展示,可批量
	 * @param empUser
	 * @return
	 */
	@RequiresPermissions("line:edit")
	@RequestMapping(value = "setHomeLine")
	@ResponseBody
	public String setHomeLine(String lineKid, Integer lineDisplay) {

		if (StringUtils.isEmpty(lineKid)){

			return renderResult(Global.FALSE, "操作失败,lineKid不能为空！");
		}

		LineDisplayInVo lineDisplayInVo = new LineDisplayInVo();
		lineDisplayInVo.setLineKids(lineKid);
		lineDisplayInVo.setLineDisplay(lineDisplay);

		lineService.setLineToHome(lineDisplayInVo);
		return renderResult(Global.TRUE, text("展示路线'{0}'成功", lineKid));
	}


	
}