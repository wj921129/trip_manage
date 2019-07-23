package com.jeesite.modules.finance.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.finance.entity.Investor;
import com.jeesite.modules.finance.service.InvestorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.jeesite.common.web.BaseController.text;
import static com.jeesite.common.web.http.ServletUtils.renderResult;

@Controller
@RequestMapping(value = "${adminPath}/investor/investorData")
@Slf4j
public class InvestorController {
    @Autowired
    private InvestorService investorService;

    /**
     * 查询列表
     */
    @RequiresPermissions("test:testData:view")
    @RequestMapping(value = {"list", ""})
    public String list(Investor investor, Model model) {
        model.addAttribute("investor", investor);
        return "modules/finance/investorDataList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("test:testData:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<Investor> listData(Investor investor, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        Page<Investor> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }
        Map<String, Object> paramterMap = new HashMap<>();
        if(!StringUtils.isEmpty(request.getParameter("investorName"))){
            paramterMap.put("investorName", request.getParameter("investorName"));
        }
        page.setOtherData(paramterMap);
        return investorService.loadInvestorList(page);
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("test:testData:view")
    @RequestMapping(value = "form")
    public String form(Investor investor, Model model) {
        model.addAttribute("inventor", investor);
        return "modules/finance/investorDataForm";
    }

    /**
     * 保存数据
     */
    @RequiresPermissions("test:testData:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated Investor investor) {
        String s = JSONObject.toJSONString(investor);
        log.info("添加投资人入参:" +s);
        investorService.addInvestor(investor);
        return renderResult(Global.TRUE, text("保存数据成功！"));

    }

    /**
     * 删除数据
     */
    @RequiresPermissions("test:testData:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(@Validated Investor investor) {
        log.info("deleteInvestor入参:" +"kid=" +investor.getKid());
        investorService.deleteInvestor(investor);
        return renderResult(Global.TRUE, text("删除数据成功！"));
    }


}
