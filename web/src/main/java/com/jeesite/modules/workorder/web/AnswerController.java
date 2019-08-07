package com.jeesite.modules.workorder.web;

import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.user.entity.UserInfo;
import com.jeesite.modules.workorder.entity.Answer;
import com.jeesite.modules.workorder.service.AnswerService;
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
@RequestMapping(value = "${adminPath}/answer")
public class AnswerController  extends BaseController {

    @Autowired
    private AnswerService answerService;

    @RequestMapping(value = {"list", ""})
    public String list(Answer answer, Model model) {
        model.addAttribute("answer", answer);
        return "modules/workorder/answerList";
    }


    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<Answer> listData(Answer answer, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<Answer> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Page<Answer> answerPage = answerService.queryAllAnswer(page);

        return answerPage;
    }

}
