package com.jeesite.modules.finance.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.finance.entity.Investor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class InvestorService {
    @Value("${api.host}")
    private String apiHost;

    public Page<Investor> loadInvestorList(Page<Investor> page){
        //构建入参对象
        JSONObject object = new JSONObject();
        object.put("pageSize",page.getPageSize());
        object.put("pageNumber",page.getPageNo());
        object.put("investorName",page.getOtherData().get("investorName"));

          String requestUrl = apiHost + "/support/invest/queryInvestorBypage";
      //  String requestUrl = "http://192.168.31.201:7150/support/invest/queryInvestorBypage";
        String result = ApiUtils.post(requestUrl, object);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            //System.err.println(JSONObject.toJSONString(resultObject));
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<Investor> investorList = (List<Investor>)resultObject.getJSONObject("data").get("entities");
                    page.setList(investorList);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));
                }
            }
        }
        return page;
    }

    //添加投资人
    public void addInvestor(Investor investor){
          String requestUrl = apiHost + "/support/invest/addInvestor";
       // String requestUrl = "http://192.168.31.201:7150/support/invest/addInvestor";
        //请求后台接口
        String result = ApiUtils.post(requestUrl, investor);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(!"200".equals(resultObject.getString("code"))){
               log.info("调用添加投资人接口失败");
            }
        }
    }

    //删除投资人
    public void deleteInvestor(Investor investor){
         String requestUrl = apiHost + "/support/invest/deleteInvestor";
       // String requestUrl = "http://192.168.31.201:7150/support/invest/deleteInvestor";
        //请求后台接口
        String result = ApiUtils.post(requestUrl, investor);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(!"200".equals(resultObject.getString("code"))){
                log.info("调用添加投资人接口失败");
            }
        }
    }


}
