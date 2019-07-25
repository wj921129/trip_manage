package com.jeesite.modules.finance.web;

import com.jeesite.common.entity.Page;
import com.jeesite.modules.finance.entity.Withdraw;
import com.jeesite.modules.finance.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(value = "${adminPath}/withdraw")
public class WithdrawController {

    @Autowired
    private WithdrawService withdrawService;


    /**
     * 查询列表
     */
    @RequestMapping(value = {"list", ""})
    public String list(Withdraw withdraw, Model model) {
        model.addAttribute("withdraw", withdraw);
        return "modules/finance/withdrawList";
    }


    /**
     * 查询列表数据
     */
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<Withdraw> listData(Withdraw withdraw, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<Withdraw> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Page<Withdraw> withdrawPage = withdrawService.queryWithdraw(page);

        return withdrawPage;
    }



}
