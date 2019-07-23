package com.jeesite.modules.finance.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.finance.entity.AuditRefundBailInVo;
import com.jeesite.modules.finance.entity.RefundBail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RefundBailService {

    @Value("${api.host}")
    private String apiHost;


    public Page<RefundBail> queryRefundBail(Page<RefundBail> page){
        Map<String, Object> otherData = page.getOtherData();

        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());
        ob.put("refundStatu", 10);
        ob.put("refundReason", otherData.get("refundReason"));

        String requestUrl = apiHost + "/support/refundBail/queryRefundBail";
        //String requestUrl = "http://192.168.31.198:6150/support/refundBail/queryRefundBail";
        String result = ApiUtils.get(requestUrl,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<RefundBail> reports = (List<RefundBail>)resultObject.getJSONObject("data").get("entities");

                    page.setList(reports);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));

                }
            }
        }

        return page;
    }


    public String auditRefundBail(AuditRefundBailInVo auditRefundBailInVo){
        String result = ApiUtils.post(apiHost + "/support/refundBail/auditRefundBail", auditRefundBailInVo);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject.getString("code");
        }
        return null;
    }


}
