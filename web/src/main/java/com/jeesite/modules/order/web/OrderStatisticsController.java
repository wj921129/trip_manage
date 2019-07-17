package com.jeesite.modules.order.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.order.service.OrderStatisticsService;
import com.jeesite.modules.user.entity.UserStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "${adminPath}/order")
public class OrderStatisticsController {
    //注入订单统计服务
    @Autowired
    private OrderStatisticsService orderStatisticsService;

    //跳转统计页面
    @RequestMapping(value = {"statistics", ""})
    public String statistics(UserStatistics userStatistics, Model model) {
        model.addAttribute("userStatistics", userStatistics);
        return "modules/order/orderStatistics";
    }

    //统计图数据
    @RequestMapping(value = "echartsData")
    @ResponseBody
    public JSONObject echartsData(HttpServletRequest request) {
        int countValue = Integer.parseInt(request.getParameter("countValue"));
        JSONObject object = orderStatisticsService.echartsData(countValue);
        return object;
    }

    //表格数据
    @RequestMapping(value = "tableData")
    @ResponseBody
    public JSONObject tableData(HttpServletRequest request) {
        int countValue = Integer.parseInt(request.getParameter("countValue"));
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        JSONObject object = orderStatisticsService.tableData(countValue, pageNo, pageSize);
        return object;
    }

    //根据起止时间查询表格数据
    @RequestMapping(value = "tableDataByTime")
    @ResponseBody
    public JSONObject tableDataByTime(HttpServletRequest request) {
        String fromDay = request.getParameter("fromDay");
        String toDay = request.getParameter("toDay");
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        JSONObject object = orderStatisticsService.tableDataByTime(fromDay, toDay, pageNo, pageSize);
        return object;
    }
}
