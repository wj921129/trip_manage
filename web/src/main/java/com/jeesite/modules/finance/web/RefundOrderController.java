package com.jeesite.modules.finance.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.finance.entity.AuditRefundOrderlInVo;
import com.jeesite.modules.finance.entity.RefundOrder;
import com.jeesite.modules.finance.service.RefundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jeesite.common.web.http.ServletUtils.renderResult;

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


    @PostMapping(value = "auditRefundOrder")
    @ResponseBody
    public String auditRefundOrder(String refundKids, Integer refundStatu, String remarks) {
        if(StringUtils.isEmpty(refundKids)){
            return renderResult(Global.FALSE, "退款订单kid不能为空！");
        }
        if(refundStatu == null){
            return renderResult(Global.FALSE, "退还审核状态不能为空！");
        }

        AuditRefundOrderlInVo auditRefundOrderlInVo = new AuditRefundOrderlInVo();
        auditRefundOrderlInVo.setRefundKids(refundKids);
        auditRefundOrderlInVo.setRefundStatu(refundStatu);
        auditRefundOrderlInVo.setRemarks(remarks);
        String result = refundOrderService.auditRefundOrder(auditRefundOrderlInVo);
        if("200".equals(result)){
            return renderResult(Global.TRUE, "成功！");
        }else{
            return renderResult(Global.FALSE, "失败！");
        }
    }



}
