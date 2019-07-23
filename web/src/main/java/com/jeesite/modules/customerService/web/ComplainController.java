package com.jeesite.modules.customerService.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.customerService.entity.Complain;
import com.jeesite.modules.customerService.entity.UpdateComplainInVo;
import com.jeesite.modules.customerService.service.ComplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.jeesite.common.web.http.ServletUtils.renderResult;

@Controller
@RequestMapping(value = "${adminPath}/complain")
public class ComplainController {

    @Autowired
    private ComplainService complainService;


    /**
     * 查询列表
     */
    @RequestMapping(value = {"list", ""})
    public String list(Complain complain, Model model) {
        model.addAttribute("complain", complain);
        return "modules/customerService/complainList";
    }


    /**
     * 查询列表数据
     */
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<Complain> listData(Complain complain, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<Complain> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Map<String, Object> paramterMap = new HashMap<>();
        if(request.getParameter("complainStatu") != null){
            paramterMap.put("complainStatu", request.getParameter("complainStatu"));
        }
        if(request.getParameter("complainReason") != null){
            paramterMap.put("complainReason", request.getParameter("complainReason"));
        }

        page.setOtherData(paramterMap);

        Page<Complain> reportPage = complainService.queryAllComplain(page);

        return reportPage;
    }


    @PostMapping(value = "updateComplain")
    @ResponseBody
    public String updateComplain(String kid, Integer complainStatu) {
        if(StringUtils.isEmpty(kid)){
            return renderResult(Global.FALSE, "投诉kid不能为空！");
        }

        if(complainStatu == null){
            return renderResult(Global.FALSE, "投诉状态不能为空！");
        }

        UpdateComplainInVo updateComplainInVo = new UpdateComplainInVo();
        updateComplainInVo.setKid(kid);
        updateComplainInVo.setComplainStatu(complainStatu);
        String result = complainService.updateComplain(updateComplainInVo);
        if("200".equals(result)){
            return renderResult(Global.TRUE, "处理成功！");
        }else{
            return renderResult(Global.FALSE, "处理失败！");
        }
    }


}
