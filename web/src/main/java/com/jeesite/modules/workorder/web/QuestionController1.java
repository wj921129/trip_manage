package com.jeesite.modules.workorder.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.modules.workorder.entity.Question1;
import com.jeesite.modules.workorder.service.QuestionService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/question1")
public class QuestionController1 extends BaseController {

    @Autowired
    private QuestionService1 questionService;

    @RequestMapping(value = {"list", ""})
    public String list(Question1 question, Model model) {
        model.addAttribute("question", question);
        return "modules/workorder/questionList1";
    }


    @RequestMapping(value = "listData")
    @ResponseBody
    public List<Question1> listData(Question1 question, HttpServletRequest request, HttpServletResponse response) {

        List<Question1> questions = questionService.questionList();

        return questions;
    }
}
