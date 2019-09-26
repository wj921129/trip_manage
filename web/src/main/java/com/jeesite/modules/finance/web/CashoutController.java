package com.jeesite.modules.finance.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.finance.entity.AgreeCashoutInVo;
import com.jeesite.modules.finance.entity.Cashout;
import com.jeesite.modules.finance.entity.Investor;
import com.jeesite.modules.finance.entity.RefuseCashoutInVo;
import com.jeesite.modules.finance.service.CashoutService;
import lombok.extern.slf4j.Slf4j;
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

@Controller
@RequestMapping(value = "${adminPath}/cashout/cashoutData")
@Slf4j
public class CashoutController extends BaseController {
    @Autowired
    private CashoutService cashoutService;

    /**
     * 查询列表
     */
    //@RequiresPermissions("test:testData:view")
    @RequestMapping(value = {"list", ""})
    public String list(Cashout cashout, Model model) {
        model.addAttribute("cashout", cashout);
        return "modules/finance/cashoutDataList";
    }

    /**
     * 查询列表数据
     */
    //@RequiresPermissions("test:testData:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<Cashout> listData(Cashout cashout, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        Page<Cashout> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }
        Map<String, Object> paramterMap = new HashMap<>();
        if(!StringUtils.isEmpty(request.getParameter("cashoutKid"))){
            paramterMap.put("cashoutKid", request.getParameter("cashoutKid"));
        }
        if(!StringUtils.isEmpty(request.getParameter("cashoutStatus"))){
            paramterMap.put("cashoutStatus", request.getParameter("cashoutStatus"));
        }
        page.setOtherData(paramterMap);
        return cashoutService.loadCashoutList(page);
    }

    /**
     * 查看编辑表单
     */
    //@RequiresPermissions("test:testData:view")
    @RequestMapping(value = "form")
    public String form(HttpServletRequest request , Model model) {
        String kid = request.getParameter("kid");
        model.addAttribute("kid", kid);
        return "modules/finance/refuseCashoutForm";
    }

    /**
     * 拒绝提现
     */
    //@RequiresPermissions("test:testData:view")
    @RequestMapping(value = "refuseCashout")
    @ResponseBody
    public String refuseCashout(HttpServletRequest request) {
        String kid = request.getParameter("kid");
        String cashoutRemarks = request.getParameter("cashoutRemarks");
        RefuseCashoutInVo refuseCashoutInVo = new RefuseCashoutInVo();
        refuseCashoutInVo.setCashoutKid(kid);
        refuseCashoutInVo.setRefuseReason(cashoutRemarks);
        String s = cashoutService.refuseCashout(refuseCashoutInVo);
        if(s.equals("success")){
            return renderResult(Global.TRUE, text("保存数据成功!"));
        }
        return renderResult(Global.TRUE, text("保存数据失败!"));
    }

    /**
     * 同意提现
     */
    //@RequiresPermissions("test:testData:view")
    @RequestMapping(value = "agreeCashout")
    @ResponseBody
    public String agreeCashout(@Validated Cashout cashout) {
        AgreeCashoutInVo agreeCashoutInVo = new AgreeCashoutInVo();
        agreeCashoutInVo.setCashoutKid(cashout.getKid());
        String s = cashoutService.agreeCashout(agreeCashoutInVo);
        if(s.equals("success")){
            return renderResult(Global.TRUE, text("保存数据成功!"));
        }
        return renderResult(Global.TRUE, text("保存数据失败!"));
    }

    /**
     * 删除提现
     */
    //@RequiresPermissions("test:testData:view")
    @RequestMapping(value = "deleteCashout")
    @ResponseBody
    public String deleteCashout(@Validated Cashout cashout) {
        AgreeCashoutInVo agreeCashoutInVo = new AgreeCashoutInVo();
        agreeCashoutInVo.setCashoutKid(cashout.getKid());
        String s = cashoutService.deleteCashout(agreeCashoutInVo);
        if(s.equals("success")){
            return renderResult(Global.TRUE, text("删除数据成功!"));
        }
        return renderResult(Global.TRUE, text("删除数据失败!"));
    }



}
