package com.jeesite.modules.finance.web;

import com.jeesite.common.entity.Page;
import com.jeesite.modules.finance.entity.RefundOrder;
import com.jeesite.modules.finance.service.RefundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/refundOrder")
public class RefundOrderController {

    @Autowired
    private RefundOrderService refundOrderService;


    /**
     * 查询列表
     */
    @RequestMapping(value = {"list", ""})
    public String list(RefundOrder refundOrder, Model model) {
        model.addAttribute("refundOrder", refundOrder);
        return "modules/finance/refundOrderList";
    }


    /**
     * 查询列表数据
     */
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<RefundOrder> listData(RefundOrder refundOrder, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<RefundOrder> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Page<RefundOrder> reportPage = refundOrderService.queryRefundOrder(page);

        return reportPage;
    }



}
