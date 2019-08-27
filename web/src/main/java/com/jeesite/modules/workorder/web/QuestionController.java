package com.jeesite.modules.workorder.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.auth.entity.AuditIdAuthInVo;
import com.jeesite.modules.workorder.entity.Question;
import com.jeesite.modules.workorder.entity.TreeQuestionOutVo;
import com.jeesite.modules.workorder.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/question")
public class QuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = {"list", ""})
    public String list(Question question, Model model) {
        model.addAttribute("question", question);
        return "modules/workorder/questionList";
    }


    /*@RequestMapping(value = "listData")
    @ResponseBody
    public List<Question> listData(Question question, HttpServletRequest request, HttpServletResponse response) {

        List<Question> questions = questionService.questionList();

        return questions;
    }*/


    @RequestMapping(value = "queryTreeQuestion")
    @ResponseBody
    public List<TreeQuestionOutVo> queryTreeQuestion() {

        List<TreeQuestionOutVo> treeQuestionOutVos = questionService.queryTreeQuestion();

        return treeQuestionOutVos;
    }


    @RequestMapping(value = {"form", ""})
    public String addQuestion(Question question, Model model) {
        model.addAttribute("question", question);
        return "modules/workorder/questionForm";
    }


    @PostMapping(value = "save")
    @ResponseBody
    public String save(Question question) {
        if(StringUtils.isEmpty(question.getQuestionName())){
            return renderResult(Global.FALSE, "问题名称不能为空！");
        }
        if(question.getQuestionOrder() == null){
            return renderResult(Global.FALSE, "问题序号不能为空！");
        }

        JSONObject jsonObject = questionService.save(question);
        if(jsonObject != null){
            if("200".equals(jsonObject.getString("code"))){
                return renderResult(Global.TRUE, "成功！");
            }else{
                return renderResult(Global.FALSE, jsonObject.getString("msg"));
            }
        }else{
            return renderResult(Global.FALSE, "失败！");
        }
    }


    @PostMapping(value = "deleteQuestion")
    @ResponseBody
    public JSONObject deleteQuestion(String kid) {
        JSONObject returnObj = questionService.deleteQuestion(kid);
        return returnObj;
    }


}
