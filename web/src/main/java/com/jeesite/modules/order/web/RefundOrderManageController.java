package com.jeesite.modules.order.web;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.finance.entity.Investor;
import com.jeesite.modules.order.entity.RefundOrderDetailsVo;
import com.jeesite.modules.order.entity.RefundOrderListVo;
import com.jeesite.modules.order.service.RefundOrderManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.jeesite.common.web.http.ServletUtils.renderResult;


@Controller
@RequestMapping(value = "${adminPath}/order/refundOrderData")
@Slf4j
public class RefundOrderManageController extends BaseController {

    @Autowired
    private RefundOrderManageService refundOrderManageService;

    /**
     * 查询列表
     */
    @RequiresPermissions("test:testData:view")
    @RequestMapping(value = {"list", ""})
    public String list(RefundOrderListVo refundOrderListVo, Model model) {
        model.addAttribute("refundOrderListVo", refundOrderListVo);
        return "modules/order/refundOrderDataList";
    }

   /**
     * 查询列表数据
     */
    @RequiresPermissions("test:testData:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<RefundOrderListVo> listData(RefundOrderListVo refundOrderListVo, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<RefundOrderListVo> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Map<String, Object> paramterMap = new HashMap<>();
        if(!StringUtils.isEmpty(request.getParameter("refundStatu"))){
            paramterMap.put("refundStatu", request.getParameter("refundStatu"));
        }
        if(!StringUtils.isEmpty(request.getParameter("refundKid"))){
            paramterMap.put("refundKid", request.getParameter("refundKid"));
        }
        page.setOtherData(paramterMap);
        return refundOrderManageService.loadRefundOrderManageList(page);
    }


    /**
     * 查询退款单详情
     */
    @RequiresPermissions("test:testData:view")
    @RequestMapping(value = "view")
    public String form(RefundOrderDetailsVo refundOrderDetailsVo, Model model) {
        log.info("查询退款单详情入参:"+ JSONObject.toJSONString(refundOrderDetailsVo));
        RefundOrderDetailsVo refundOrderDetailsVo1 = refundOrderManageService.queryRefundOrderDetails(refundOrderDetailsVo);
        if(refundOrderDetailsVo1 == null){
            log.info("查询退款单详情为空");
            return null;
        }else{
            model.addAttribute("refundOrderDetailsVo", refundOrderDetailsVo1);
            return "modules/order/refundOrderDetailsForm";
        }
    }

    /**
     * 后台通过审核
     */
    @RequiresPermissions("test:testData:edit")
    @RequestMapping(value = "examineYes")
    @ResponseBody
    public String examineYes(@Validated RefundOrderListVo refundOrderListVo) {
        log.info("后台同意退款入参:" +JSONObject.toJSONString(refundOrderListVo));
        //调用通过审核接口
        refundOrderManageService.backstageAgreeRefund(refundOrderListVo);
        return renderResult(Global.TRUE, text("审核成功!"));
    }

}
