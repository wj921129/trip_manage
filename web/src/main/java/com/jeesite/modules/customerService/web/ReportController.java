package com.jeesite.modules.customerService.web;

import com.jeesite.common.entity.Page;
import com.jeesite.modules.customerService.entity.Report;
import com.jeesite.modules.customerService.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/report")
public class ReportController {

    @Autowired
    private ReportService reportService;


    /**
     * 查询列表
     */
    @RequestMapping(value = {"list", ""})
    public String list(Report report, Model model) {
        model.addAttribute("report", report);
        return "modules/customerService/reportList";
    }


    /**
     * 查询列表数据
     */
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<Report> listData(Report report, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<Report> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Map<String, Object> paramterMap = new HashMap<>();
        if(request.getParameter("reportReason") != null){
            paramterMap.put("reportReason", request.getParameter("reportReason"));
        }
        if(!StringUtils.isEmpty(request.getParameter("discoverKid"))){
            paramterMap.put("discoverKid", request.getParameter("discoverKid"));
        }

        page.setOtherData(paramterMap);

        Page<Report> reportPage = reportService.queryAllReport(page);

        return reportPage;
    }



}
