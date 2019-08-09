package com.jeesite.modules.workorder.web;

import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.test.entity.TestTree;
import com.jeesite.modules.workorder.entity.Answer;
import com.jeesite.modules.workorder.entity.Question;
import com.jeesite.modules.workorder.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/question")
public class QuestionController  extends BaseController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = {"list", ""})
    public String list(Question question, Model model) {
        model.addAttribute("question", question);
        return "modules/workorder/questionList";
    }


    @RequestMapping(value = "listData")
    @ResponseBody
    public List<Question> listData(Question question, HttpServletRequest request, HttpServletResponse response) {

        List<Question> questions = questionService.questionList();

        return questions;
    }


    /*@RequestMapping(value = "listData")
    @ResponseBody
    public List<Question> listData(Question question) {
        List<Question> list = questionService.findList(question);
        return list;
    }*/

}
