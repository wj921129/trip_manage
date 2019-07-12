package com.jeesite.modules.user.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.user.entity.UserInfo;
import com.jeesite.modules.user.entity.UserStatistics;
import com.jeesite.modules.user.service.UserInfoService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/user")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;


    @RequestMapping(value = {"list", ""})
    public String list(UserInfo user, Model model) {
        model.addAttribute("user", user);
        return "modules/user/userList";
    }


    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<UserInfo> listData(UserInfo user, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<UserInfo> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Map<String, Object> paramterMap = new HashMap<>();
        if(!StringUtils.isEmpty(request.getParameter("phone"))){
            paramterMap.put("phone", request.getParameter("phone"));
        }
        page.setOtherData(paramterMap);

        Page<UserInfo> userInfoPage = userInfoService.queryAllUser(page);

        return userInfoPage;
    }


    @RequestMapping(value = {"statistics", ""})
    public String statistics(UserStatistics userStatistics, Model model) {
        model.addAttribute("userStatistics", userStatistics);
        return "modules/user/userStatistics";
    }



    @RequestMapping(value = "echartsData")
    @ResponseBody
    public JSONObject echartsData(HttpServletRequest request) {
        int countValue = Integer.parseInt(request.getParameter("countValue"));

        JSONObject object = userInfoService.echartsData(countValue);

        return object;
    }


    @RequestMapping(value = "tableData")
    @ResponseBody
    public JSONObject tableData(HttpServletRequest request) {
        int countValue = Integer.parseInt(request.getParameter("countValue"));
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        JSONObject object = userInfoService.tableData(countValue, pageNo, pageSize);

        return object;
    }


    @RequestMapping(value = "tableDataByTime")
    @ResponseBody
    public JSONObject tableDataByTime(HttpServletRequest request) {
        String fromDay = request.getParameter("fromDay");
        String toDay = request.getParameter("toDay");
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        JSONObject object = userInfoService.tableDataByTime(fromDay, toDay, pageNo, pageSize);

        return object;
    }


}
