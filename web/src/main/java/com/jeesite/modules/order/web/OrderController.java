package com.jeesite.modules.order.web;
;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.order.entity.OrderListVo;
import com.jeesite.modules.order.service.OrderService;
import com.jeesite.modules.test.entity.TestData;
import com.jeesite.modules.user.entity.UserInfo;
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
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "${adminPath}/order/orderData")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    /**
     * 查询列表
     */
    @RequiresPermissions("test:testData:view")
    @RequestMapping(value = {"list", ""})
    public String list(OrderListVo order, Model model) {
        model.addAttribute("order", order);
        return "modules/order/orderDataList";
    }

   /**
     * 查询列表数据
     */
    @RequiresPermissions("test:testData:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<OrderListVo> listData(OrderListVo orderListVo, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<OrderListVo> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Map<String, Object> paramterMap = new HashMap<>();
        if(!StringUtils.isEmpty(request.getParameter("orderStatu"))){
            paramterMap.put("orderStatu", request.getParameter("orderStatu"));
        }
        if(!StringUtils.isEmpty(request.getParameter("orderKid"))){
            paramterMap.put("orderKid", request.getParameter("orderKid"));
        }
        page.setOtherData(paramterMap);
        return orderService.loadOrderManageList(page);
    }


    /**
     * 查看编辑表单
     */
    @RequiresPermissions("test:testData:view")
    @RequestMapping(value = "form")
    public String form(OrderListVo orderListVo, Model model) {
        model.addAttribute("order", orderListVo);
        return "modules/order/orderDataForm";
    }

    /**
     * 保存数据
     */
    @RequiresPermissions("test:testData:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated OrderListVo orderListVo) {
        //调用保存订单接口服务
        return renderResult(Global.TRUE, text("保存数据成功！"));
    }


}
