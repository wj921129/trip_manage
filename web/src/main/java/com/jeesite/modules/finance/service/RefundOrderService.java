package com.jeesite.modules.finance.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.finance.entity.AuditRefundOrderlInVo;
import com.jeesite.modules.finance.entity.RefundOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class RefundOrderService {

    @Value("${api.host}")
    private String apiHost;

    private static final String queryRefundByStatu = "/order/refund/queryRefundByStatu";

    private static final String auditRefund = "/order/refund/auditRefund";

    public Page<RefundOrder> queryRefundOrder(Page<RefundOrder> page){

        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());
        ob.put("refundStatu", 20);

        String result = ApiUtils.get(apiHost + queryRefundByStatu,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<RefundOrder> reports = (List<RefundOrder>)resultObject.getJSONObject("data").get("entities");

                    page.setList(reports);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));

                }
            }
        }

        return page;
    }



    public String auditRefundOrder(AuditRefundOrderlInVo auditRefundOrderlInVo){
        String result = ApiUtils.post(apiHost + auditRefund, auditRefundOrderlInVo);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject.getString("code");
        }
        return null;
    }


}
