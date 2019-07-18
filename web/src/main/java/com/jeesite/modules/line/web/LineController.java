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
import org.apache.commons.beanutils.BeanUtils;
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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
//	@ResponseBody
	public String form(SearchLineInVo searchLineInVo, Model model, HttpServletRequest request) {

		LineDetailsOutVo lineDetails = new LineDetailsOutVo();

		ScheduleOutVo scheduleOutVo = lineService.querySchedule(searchLineInVo.getLineKid(), searchLineInVo.getUserKid());
		LineInfoOutVo lineInfo = scheduleOutVo.getLine();
		lineDetails.setLineInfo(lineInfo);
		model.addAttribute("lineInfo", lineInfo);

		LineTouristInfoOutVo touristInfo = scheduleOutVo.getTouristInfo();
		lineDetails.setTouristInfo(touristInfo);
		model.addAttribute("touristInfo", touristInfo);

		LineCostOutVo lineCost = scheduleOutVo.getLineCost();
		lineDetails.setLineCost(lineCost);
		model.addAttribute("lineCost", lineCost);

		LineFeatureOutVo lineFeature = new LineFeatureOutVo();
		lineFeature.setLineFeature(scheduleOutVo.getLineFeature());
		lineDetails.setLineFeature(lineFeature);
		model.addAttribute("lineFeature", lineFeature);

		LineCommentOtherOutVo lineCommentOtherOutVo = new LineCommentOtherOutVo();
		LineCommentOutVo lineComment = scheduleOutVo.getLineComment();
		lineCommentOtherOutVo.setCommentContent(lineComment.getCommentContent());
		lineCommentOtherOutVo.setCommentNum(scheduleOutVo.getCommentNum());
		lineCommentOtherOutVo.setCommentTime(lineComment.getCommentTime());
		lineCommentOtherOutVo.setCommentUserBail(lineComment.getCommentUserBail());
		lineCommentOtherOutVo.setCommentUserImage(lineComment.getCommentUserImage());
		lineCommentOtherOutVo.setCommentUserKid(lineComment.getCommentUserKid());
		lineCommentOtherOutVo.setCommentUserNickname(lineComment.getCommentUserNickname());
		lineCommentOtherOutVo.setCommentUserStarLevel(lineComment.getCommentUserStarLevel());
		lineDetails.setLineComment(lineCommentOtherOutVo);
		model.addAttribute("lineComment", lineCommentOtherOutVo);

		List<ScheduleDetailsOutVo> scheduleDetailsOutVos = new ArrayList<>();
		List<LineScheduleOutVo> scheduleList = scheduleOutVo.getScheduleList();
		for (int i = 0; i < scheduleList.size(); i++) {

			LineScheduleOutVo lineScheduleOutVo = scheduleList.get(i);
			List<SchedulingOutVo> lineSchedules = lineScheduleOutVo.getLineSchedules();
			List<DinnerOutVo> lineDinners = lineScheduleOutVo.getLineDinners();

			ScheduleDetailsOutVo scheduleDetailsOutVo = new ScheduleDetailsOutVo();


			ScheduleDinnerOutVo dinner = new ScheduleDinnerOutVo();

			scheduleDetailsOutVo.setLineDay(lineScheduleOutVo.getLineDay());

			ScheduleAfternoonOutVo morning = new ScheduleAfternoonOutVo();
			ScheduleAfternoonOutVo afternoon = new ScheduleAfternoonOutVo();
			ScheduleAfternoonOutVo night = new ScheduleAfternoonOutVo();

			for (int j = 0; j < lineSchedules.size(); j++) {

				ScheduleActivityOutVo activity = new ScheduleActivityOutVo();

				SchedulingOutVo schedulingOutVo = lineSchedules.get(j);

				activity.setActivityDesc(schedulingOutVo.getActivityDesc());
				activity.setActivityName(schedulingOutVo.getActivityName());
				activity.setGuideCost(schedulingOutVo.getGuideCost());
				activity.setKid(schedulingOutVo.getKid());
				activity.setSchedulingAddress(schedulingOutVo.getSchedulingAddress());
				activity.setSchedulingPics(schedulingOutVo.getSchedulingPics());
				activity.setSchedulingPrice(schedulingOutVo.getSchedulingPrice());
				activity.setSchedulingType(schedulingOutVo.getSchedulingType());

				if (schedulingOutVo.getSchedulingType() == 10){
					morning.setScheduleActivityOutVo(activity);
				}else if (schedulingOutVo.getSchedulingType() == 20){
					afternoon.setScheduleActivityOutVo(activity);
				}else if (schedulingOutVo.getSchedulingType() == 30){
					night.setScheduleActivityOutVo(activity);
				}
			}

				for (int j = 0; j < lineDinners.size(); j++) {
				DinnerOutVo dinnerOutVo = lineDinners.get(j);

				ScheduleDinnerOutVo scheduleDinnerOutVo = new ScheduleDinnerOutVo();
				scheduleDinnerOutVo.setDinnerDesc(dinnerOutVo.getDinnerDesc());
				scheduleDinnerOutVo.setDinnerIsSelf(dinnerOutVo.getDinnerIsSelf());
				scheduleDinnerOutVo.setDinnerPics(dinnerOutVo.getDinnerPics());
				scheduleDinnerOutVo.setDinnerPrice(dinnerOutVo.getDinnerPrice());
				scheduleDinnerOutVo.setDinnerType(dinnerOutVo.getDinnerType());

				if (dinnerOutVo.getDinnerType() == 10){
					morning.setScheduleDinnerOutVo(scheduleDinnerOutVo);
				}else if (dinnerOutVo.getDinnerType() == 20){
					afternoon.setScheduleDinnerOutVo(scheduleDinnerOutVo);
				}else if (dinnerOutVo.getDinnerType() == 30){
					night.setScheduleDinnerOutVo(scheduleDinnerOutVo);
				}
			}

			scheduleDetailsOutVo.setAfternoon(afternoon);
			scheduleDetailsOutVo.setMorning(morning);
			scheduleDetailsOutVo.setNight(night);

			ScheduleStayOutVo stay = new ScheduleStayOutVo();
			List<StayOutVo> lineStays = lineScheduleOutVo.getLineStays();
			StayOutVo stayOutVo = lineStays.get(0);
			stay.setKid(stayOutVo.getKid());
			stay.setStayAddress(stayOutVo.getStayAddress());
			stay.setStayDesc(stayOutVo.getStayDesc());
			stay.setStayPics(stayOutVo.getStayPics());
			stay.setStayPrice(stayOutVo.getStayPrice());
			stay.setStayType(stayOutVo.getStayType());
			scheduleDetailsOutVo.setStay(stay);

			ScheduleTrafficOutVo traffic = new ScheduleTrafficOutVo();
			List<TrafficOutVo> lineTraffics = lineScheduleOutVo.getLineTraffics();
			TrafficOutVo trafficOutVo = lineTraffics.get(0);
			traffic.setKid(trafficOutVo.getKid());
			traffic.setTrafficDesc(trafficOutVo.getTrafficDesc());
			traffic.setTrafficPrice(trafficOutVo.getTrafficPrice());

			scheduleDetailsOutVo.setTraffic(traffic);
			scheduleDetailsOutVos.add(scheduleDetailsOutVo);

		}

		lineDetails.setScheduleDetailsOutVos(scheduleDetailsOutVos);
		model.addAttribute("scheduleInfo", scheduleDetailsOutVos);
		model.addAttribute("lineDetails", lineDetails);

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