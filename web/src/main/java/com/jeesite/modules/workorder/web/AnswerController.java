package com.jeesite.modules.workorder.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.workorder.entity.Answer;
import com.jeesite.modules.workorder.entity.AnswerUpdateInVo;
import com.jeesite.modules.workorder.entity.Question;
import com.jeesite.modules.workorder.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/answer")
public class AnswerController extends BaseController {

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


    @RequestMapping(value = {"form", ""})
    public String addQuestion(Answer answer, Model model) {
        model.addAttribute("answer", answer);
        return "modules/workorder/answerForm";
    }


    @PostMapping(value = "save")
    @ResponseBody
    public String save(Answer answer) {
        if(StringUtils.isEmpty(answer.getAnswer())){
            return renderResult(Global.FALSE, "答案不能为空！");
        }

        JSONObject jsonObject = answerService.save(answer);
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


    @PostMapping(value = "updateAnswer")
    @ResponseBody
    public String updateAnswer(String kid, String answer) {
        if (StringUtils.isEmpty(kid)) {
            return renderResult(Global.FALSE, "答案kid不能为空！");
        }

        if (StringUtils.isEmpty(answer)) {
            return renderResult(Global.FALSE, "答案不能为空！");
        }

        AnswerUpdateInVo answerUpdateInVo = new AnswerUpdateInVo();
        answerUpdateInVo.setKid(kid);
        answerUpdateInVo.setAnswer(answer);
        String result = answerService.updateAnswer(answerUpdateInVo);
        if ("200".equals(result)) {
            return renderResult(Global.TRUE, "修改成功！");
        } else {
            return renderResult(Global.FALSE, "修改失败！");
        }
    }


    @RequestMapping(value = "deleteAnswer")
    @ResponseBody
    public String deleteAnswer(String kid) {
        if (StringUtils.isEmpty(kid)) {
            return renderResult(Global.FALSE, "答案kid不能为空！");
        }

        String result = answerService.deleteAnswer(kid);
        if ("200".equals(result)) {
            return renderResult(Global.TRUE, "删除成功！");
        } else {
            return renderResult(Global.FALSE, "删除失败！");
        }
    }


}
