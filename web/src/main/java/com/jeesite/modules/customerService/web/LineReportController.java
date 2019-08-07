package com.jeesite.modules.customerService.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.customerService.entity.LineReport;
import com.jeesite.modules.customerService.service.LineReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/lineReport")
public class LineReportController  extends BaseController {

    @Autowired
    private LineReportService lineReportService;


    /**
     * 查询列表
     */
    @RequestMapping(value = {"list", ""})
    public String list(LineReport lineReport, Model model) {
        model.addAttribute("withdraw", lineReport);
        return "modules/customerService/lineReportList";
    }


    /**
     * 查询列表数据
     */
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<LineReport> listData(LineReport lineReport, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<LineReport> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Page<LineReport> lineReportPage = lineReportService.queryLineReport(page);

        return lineReportPage;
    }




    @RequestMapping(value = "lineReportData")
    @ResponseBody
    public JSONObject lineReportData(HttpServletRequest request) {
        String lineKid = request.getParameter("lineKid");
        JSONObject object = lineReportService.lineReportData(lineKid);

        return object;
    }


}
