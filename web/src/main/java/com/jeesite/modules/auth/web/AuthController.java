package com.jeesite.modules.auth.web;

import com.jeesite.common.entity.Page;
import com.jeesite.modules.auth.entity.EnterpriseAuth;
import com.jeesite.modules.auth.entity.IdAuth;
import com.jeesite.modules.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = {"idAuthList", ""})
    public String idAuth(IdAuth idAuth, Model model) {
        model.addAttribute("idAuth", idAuth);
        return "modules/auth/idAuthList";
    }


    @RequestMapping(value = {"enterpriseAuthList", ""})
    public String enterpriseAuthList(EnterpriseAuth enterpriseAuth, Model model) {
        model.addAttribute("enterpriseAuth", enterpriseAuth);
        return "modules/auth/enterpriseAuthList";
    }


    @RequestMapping(value = "idAuthListData")
    @ResponseBody
    public Page<IdAuth> idAuthListData(IdAuth idAuth, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<IdAuth> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Page<IdAuth> idAuthPage = authService.queryIdAuthInfo(page);

        return idAuthPage;
    }


    @RequestMapping(value = "enterpriseAuthListData")
    @ResponseBody
    public Page<EnterpriseAuth> enterpriseAuthListData(EnterpriseAuth enterpriseAuth, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<EnterpriseAuth> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Page<EnterpriseAuth> enterpriseAuthPage = authService.queryEnterpriseAuthInfo(page);

        return enterpriseAuthPage;
    }

}
