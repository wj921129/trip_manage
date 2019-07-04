package com.jeesite.modules.user.web;

import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.user.entity.UserInfo;
import com.jeesite.modules.user.service.UserInfoService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/user")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;


    /**
     * 查询列表
     */
    @RequestMapping(value = {"list", ""})
    public String list(UserInfo user, Model model) {
        model.addAttribute("user", user);
        return "modules/user/userList";
    }


    /**
     * 查询列表数据
     */
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

        Page<UserInfo> userInfoPage = userInfoService.queryAllUser(page);

        return userInfoPage;
    }

}
