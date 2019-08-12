package com.jeesite.modules.finance.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.finance.entity.AuditRefundOrderlInVo;
import com.jeesite.modules.finance.entity.Withdraw;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class WithdrawService {

    @Value("${api.host}")
    private String apiHost;

    private static final String queryWalletWithdrawList = "/user/userWallet/queryWalletWithdrawList";

    public Page<Withdraw> queryWithdraw(Page<Withdraw> page){

        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());
        ob.put("withdrawStatu", 10);

        String result = ApiUtils.get(apiHost + queryWalletWithdrawList,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<Withdraw> withdraws = (List<Withdraw>)resultObject.getJSONObject("data").get("entities");

                    page.setList(withdraws);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));

                }
            }
        }

        return page;
    }



}
