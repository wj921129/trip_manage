/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.workorder.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.idgen.IdWorker;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.workorder.entity.Answer;
import com.jeesite.modules.workorder.entity.Question;
import com.jeesite.modules.workorder.service.AnswerService;
import com.jeesite.modules.workorder.service.QuestionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/answer")
public class AnswerController extends BaseController {

	@Autowired
	private AnswerService answerService;

	@Autowired
	private QuestionService questionService;

	private IdWorker idWorker = new IdWorker(0, 0);

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Answer get(Answer answer) {
		return answerService.get(answer);
	}
	
	@RequiresPermissions("workorder:answer:view")
	@RequestMapping(value = "list")
	public String list(Answer answer, Model model) {
		return "modules/workorder/answerList";
	}
	
	@RequiresPermissions("workorder:answer:view")
	@RequestMapping(value = {"listData"})
	@ResponseBody
	public Page<Answer> listData(Answer answer, HttpServletRequest request, HttpServletResponse response) {
		answer.setPage(new Page<>(request, response));
		Page<Answer> page = answerService.findPage(answer);
		List<Answer> list = page.getList();
		if(list != null && list.size() > 0){
			for (int i=0;i<list.size();i++){
				Question searchQuestion = new Question();
				searchQuestion.setKid(list.get(i).getQuestionKid());
				List<Question> questions = questionService.findList(searchQuestion);
				list.get(i).setQuestionName(questions.get(0).getQuestionName());
			}
		}
		return page;
	}



	@RequiresPermissions("workorder:answer:view")
	@RequestMapping(value = "form")
	public String form(Answer answer, Model model) {
		Question question = new Question();
		model.addAttribute("question", question);
		return "modules/workorder/answerForm";
	}


	@RequiresPermissions("workorder:answer:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Answer answer) {
		String questionKid = answer.getQuestionKid();
		Question searchQuestion = new Question();
		searchQuestion.setQuestionCode(questionKid);
		Question question = questionService.get(searchQuestion);
		questionKid = question.getKid();

		Answer searchAnswer = new Answer();
		searchAnswer.setQuestionKid(questionKid);
		List<Answer> list = answerService.findList(searchAnswer);
		if(list != null && list.size() > 0){
			return renderResult(Global.FALSE, text("该问题已有答案，请删除或者修改！"));
		}else{
			answer.setQuestionKid(questionKid);
			answerService.save(answer);
			return renderResult(Global.TRUE, text("保存数据成功！"));
		}

	}


	@RequiresPermissions("workorder:answer:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Answer answer) {
		answerService.delete(answer);
		return renderResult(Global.TRUE, text("删除数据成功！"));
	}


	@RequiresPermissions("workorder:answer:edit")
	@RequestMapping(value = "updateForm")
	public String updateForm(Answer answer, Model model) {
		answer = answerService.get(answer);
		String questionKid = answer.getQuestionKid();
		Question searchQuestion = new Question();
		searchQuestion.setKid(questionKid);
		List<Question> questions = questionService.findList(searchQuestion);

		answer.setQuestionName(questions.get(0).getQuestionName());
		model.addAttribute("answer", answer);
		return "modules/workorder/answerUpdateForm";
	}


	@RequiresPermissions("workorder:answer:edit")
	@PostMapping(value = "update")
	@ResponseBody
	public String update(@Validated Answer answer) {
		answerService.update(answer);
		return renderResult(Global.TRUE, text("保存数据成功！"));
	}

}