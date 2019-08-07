package com.jeesite.modules.finance.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.finance.entity.AuditRefundBailInVo;
import com.jeesite.modules.finance.entity.RefundBail;
import com.jeesite.modules.finance.service.RefundBailService;
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
@RequestMapping(value = "${adminPath}/refundBail")
public class RefundBailController  extends BaseController {

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



    @PostMapping(value = "auditRefundBail")
    @ResponseBody
    public String auditRefundBail(String refundBailKids, Integer refundStatu, String remarks) {
        if(StringUtils.isEmpty(refundBailKids)){
            return renderResult(Global.FALSE, "退还保证金kid不能为空！");
        }
        if(refundStatu == null){
            return renderResult(Global.FALSE, "退还审核状态不能为空！");
        }

        AuditRefundBailInVo auditRefundBailInVo = new AuditRefundBailInVo();
        auditRefundBailInVo.setRefundBailKids(refundBailKids);
        auditRefundBailInVo.setRefundStatu(refundStatu);
        auditRefundBailInVo.setRemarks(remarks);
        String result = refundBailService.auditRefundBail(auditRefundBailInVo);
        if("200".equals(result)){
            return renderResult(Global.TRUE, "成功！");
        }else{
            return renderResult(Global.FALSE, "失败！");
        }
    }

}
