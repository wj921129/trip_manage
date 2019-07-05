package com.jeesite.modules.finance.web;

import com.jeesite.common.entity.Page;
import com.jeesite.modules.finance.entity.RefundBail;
import com.jeesite.modules.finance.service.RefundBailService;
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
@RequestMapping(value = "${adminPath}/refundBail")
public class RefundBailController {

    @Autowired
    private RefundBailService refundBailService;


    /**
     * 查询列表
     */
    @RequestMapping(value = {"list", ""})
    public String list(RefundBail refundBail, Model model) {
        model.addAttribute("refundBail", refundBail);
        return "modules/finance/refundBailList";
    }


    /**
     * 查询列表数据
     */
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<RefundBail> listData(RefundBail refundBail, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<RefundBail> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Map<String, Object> paramterMap = new HashMap<>();
        if(request.getParameter("refundReason") != null){
            paramterMap.put("refundReason", request.getParameter("refundReason"));
        }

        page.setOtherData(paramterMap);

        Page<RefundBail> reportPage = refundBailService.queryRefundBail(page);

        return reportPage;
    }



}
