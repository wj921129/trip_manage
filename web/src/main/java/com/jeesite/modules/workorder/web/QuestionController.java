package com.jeesite.modules.workorder.web;

import com.jeesite.common.idgen.IdWorker;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.workorder.entity.Question;
import com.jeesite.modules.workorder.service.QuestionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.config.Global;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/question")
public class QuestionController  extends BaseController {

    @Autowired
    private QuestionService questionService;

    private IdWorker idWorker = new IdWorker(0, 0);

    /**
     * 获取数据
     */
    @ModelAttribute
    public Question get(String questionCode, boolean isNewRecord) {
        return questionService.get(questionCode, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("workorder:question:view")
    @RequestMapping(value = {"list", ""})
    public String list(Question question, Model model) {
        model.addAttribute("question", question);
        return "modules/workorder/questionList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("workorder:question:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public List<Question> listData(Question question) {
        if (StringUtils.isBlank(question.getParentCode())) {
            question.setParentCode(Question.ROOT_CODE);
        }
        if (StringUtils.isNotBlank(question.getQuestionName())){
            question.setParentCode(null);
        }
        if (StringUtils.isNotBlank(question.getRemarks())){
            question.setParentCode(null);
        }
        List<Question> list = questionService.findList(question);
        return list;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("workorder:question:view")
    @RequestMapping(value = "form")
    public String form(Question question, Model model) {
        // 创建并初始化下一个节点信息
        question = createNextNode(question);
        model.addAttribute("question", question);
        return "modules/workorder/questionForm";
    }

    /**
     * 查看编辑表单
     */
    /*@RequiresPermissions("workorder:question:view")
    @RequestMapping(value = "addAnswer")
    public String form1(Question question, Model model) {
        // 创建并初始化下一个节点信息
        question = createNextNode(question);
        model.addAttribute("question", question);
        return "modules/workorder/addAnswer";
    }*/

    /**
     * 创建并初始化下一个节点信息，如：排序号、默认值
     */
    @RequiresPermissions("workorder:question:edit")
    @RequestMapping(value = "createNextNode")
    @ResponseBody
    public Question createNextNode(Question question) {
        if (StringUtils.isNotBlank(question.getParentCode())){
            question.setParent(questionService.get(question.getParentCode()));
        }
        if (question.getIsNewRecord()) {
            Question where = new Question();
            where.setParentCode(question.getParentCode());
            Question last = questionService.getLastByParentCode(where);
            // 获取到下级最后一个节点
            if (last != null){
                question.setTreeSort(last.getTreeSort() + 30);
                question.setQuestionCode(IdGen.nextCode(last.getQuestionCode()));
            }else if (question.getParent() != null){
                question.setQuestionCode(question.getParent().getQuestionCode() + "001");
            }
        }
        // 以下设置表单默认数据
        if (question.getTreeSort() == null){
            question.setTreeSort(Question.DEFAULT_TREE_SORT);
        }
        return question;
    }

    /**
     * 保存数据
     */
    @RequiresPermissions("workorder:question:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated Question question) {
        String kid = question.getKid();
        if(kid == null || "".equals(kid)){
            kid = String.valueOf(idWorker.nextId());
            question.setKid(kid);
        }
        questionService.save(question);
        return renderResult(Global.TRUE, text("保存数据成功！"));
    }

    /**
     * 停用数据
     */
    @RequiresPermissions("workorder:question:edit")
    @RequestMapping(value = "disable")
    @ResponseBody
    public String disable(Question question) {
        Question where = new Question();
        where.setStatus(Question.STATUS_NORMAL);
        where.setParentCodes("," + question.getId() + ",");
        long count = questionService.findCount(where);
        if (count > 0) {
            return renderResult(Global.FALSE, text("该数据包含未停用的子数据！"));
        }
        question.setStatus(Question.STATUS_DISABLE);
        questionService.updateStatus(question);
        return renderResult(Global.TRUE, text("停用数据成功"));
    }

    /**
     * 启用数据
     */
    @RequiresPermissions("workorder:question:edit")
    @RequestMapping(value = "enable")
    @ResponseBody
    public String enable(Question question) {
        question.setStatus(Question.STATUS_NORMAL);
        questionService.updateStatus(question);
        return renderResult(Global.TRUE, text("启用数据成功"));
    }

    /**
     * 删除数据
     */
    @RequiresPermissions("workorder:question:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(Question question) {
        questionService.delete(question);
        return renderResult(Global.TRUE, text("删除数据成功！"));
    }

    /**
     * 获取树结构数据
     * @param excludeCode 排除的Code
     * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
     * @return
     */
    @RequiresPermissions("workorder:question:view")
    @RequestMapping(value = "treeData")
    @ResponseBody
    public List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
        List<Map<String, Object>> mapList = ListUtils.newArrayList();
        List<Question> list = questionService.findList(new Question());
        for (int i=0; i<list.size(); i++){
            Question e = list.get(i);
            // 过滤非正常的数据
            if (!Question.STATUS_NORMAL.equals(e.getStatus())){
                continue;
            }
            // 过滤被排除的编码（包括所有子级）
            if (StringUtils.isNotBlank(excludeCode)){
                if (e.getId().equals(excludeCode)){
                    continue;
                }
                if (e.getParentCodes().contains("," + excludeCode + ",")){
                    continue;
                }
            }
            Map<String, Object> map = MapUtils.newHashMap();
            map.put("id", e.getId());
            map.put("pId", e.getParentCode());
            map.put("kid", e.getKid());
            map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getQuestionCode(), e.getQuestionName()));
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 修复表结构相关数据
     */
    @RequiresPermissions("workorder:question:edit")
    @RequestMapping(value = "fixTreeData")
    @ResponseBody
    public String fixTreeData(Question question){
        if (!UserUtils.getUser().isAdmin()){
            return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
        }
        questionService.fixTreeData();
        return renderResult(Global.TRUE, "数据修复成功");
    }

}
