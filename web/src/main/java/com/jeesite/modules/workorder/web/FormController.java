package com.jeesite.modules.workorder.web;

import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.workorder.entity.Answer;
import com.jeesite.modules.workorder.entity.Form;
import com.jeesite.modules.workorder.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/form")
public class FormController  extends BaseController {

    @Autowired
    private FormService formService;

    @RequestMapping(value = {"list", ""})
    public String list(Form form, Model model) {
        model.addAttribute("form", form);
        return "modules/workorder/formList";
    }


    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<Form> listData(Form form, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<Form> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Page<Form> formPage = formService.queryAllForm(page);

        return formPage;
    }

}
