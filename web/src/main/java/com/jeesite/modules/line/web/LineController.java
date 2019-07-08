/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.line.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.commom.utils.ApiPage;
import com.jeesite.modules.line.entity.AuditLinesInVo;
import com.jeesite.modules.line.entity.Line;
import com.jeesite.modules.line.entity.LineSearchOutVo;
import com.jeesite.modules.line.entity.SearchLineInVo;
import com.jeesite.modules.line.service.LineService;
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
	 * 查询列表
	 */
	@RequiresPermissions("line:view")
	@RequestMapping(value = {"list", ""})
	public String list(SearchLineInVo line, Model model) {
		model.addAttribute("line", line);
		return "modules/line/lineList";
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("line:view")
	@RequestMapping(value = "audits")
	public String audits(SearchLineInVo line, Model model) {

		model.addAttribute("line", line);
		return "modules/line/auditLine";
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
		int pageSize = searchLineInVo.getPageSize() != null ? searchLineInVo.getPageSize() : 10;
		page.setPageSize(pageSize);

		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("line:view")
	@RequestMapping(value = "form")
	public String form(SearchLineInVo searchLineInVo, Model model, HttpServletRequest request) {

		page(request, searchLineInVo);
		ApiPage<LineSearchOutVo> lineSearchOutVoApiPage = lineService.queryAll(searchLineInVo);
		Page<LineSearchOutVo> page = new Page();
		page.setList(lineSearchOutVoApiPage.getEntities());
		page.setCount(lineSearchOutVoApiPage.getCount());
		page.setPageNo(searchLineInVo.getPageNumber());
		int pageSize = searchLineInVo.getPageSize() != null ? searchLineInVo.getPageSize() : 10;
		page.setPageSize(pageSize);

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
	public String delete(String lineKid) {

		if (StringUtils.isEmpty(lineKid)){

			return renderResult(Global.FALSE, "操作失败,lineKid不能为空！");
		}

		lineService.delete(lineKid);
		return renderResult(Global.TRUE, text("删除路线'{0}'成功", lineKid));
	}

	/**
	 * 上线
	 * @param empUser
	 * @return
	 */
	@RequiresPermissions("line:edit")
	@RequestMapping(value = "update")
	@ResponseBody
	public String update(String lineKid, Integer lineStatus) {

		if (StringUtils.isEmpty(lineKid)){

			return renderResult(Global.FALSE, "操作失败,lineKid不能为空！");
		}

		AuditLinesInVo auditLinesInVo = new AuditLinesInVo();
		auditLinesInVo.setLineKids(lineKid);
		auditLinesInVo.setLineStatus(lineStatus);

		lineService.update(auditLinesInVo);
		return renderResult(Global.TRUE, text("审核路线'{0}'成功", lineKid));
	}
	
}