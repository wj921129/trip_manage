package com.jeesite.modules.finance.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.finance.entity.AgreeCashoutInVo;
import com.jeesite.modules.finance.entity.Cashout;
import com.jeesite.modules.finance.entity.Investor;
import com.jeesite.modules.finance.entity.RefuseCashoutInVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.xml.ws.Response;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class CashoutService {
    @Value("${api.host}")
    private String apiHost;

    public Page<Cashout> loadCashoutList(Page<Cashout> page){
        //构建入参对象
        JSONObject object = new JSONObject();
        object.put("pageSize",page.getPageSize());
        object.put("pageNumber",page.getPageNo());
        object.put("cashoutKid",page.getOtherData().get("cashoutKid"));
        object.put("cashoutStatus",page.getOtherData().get("cashoutStatus"));

        //String requestUrl = apiHost + "/support/cashout/queryCashoutList";
          String requestUrl = "http://192.168.31.201:7150/support/cashout/queryCashoutList";
        String result = ApiUtils.post(requestUrl, object);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<Cashout> cashoutList = (List<Cashout>)resultObject.getJSONObject("data").get("entities");
                    page.setList(cashoutList);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));
                }
            }
        }
        return page;
    }


    //拒绝提现
    public String refuseCashout (RefuseCashoutInVo refuseCashoutInVo){
        JSONObject object = new JSONObject();
        object.put("cashoutKid",refuseCashoutInVo.getCashoutKid());
        object.put("refuseReason",refuseCashoutInVo.getRefuseReason());
         String requestUrl = apiHost + "/support/cashout/refuseCashout";
       // String requestUrl = "http://192.168.31.201:7150/support/cashout/refuseCashout";
        String result = ApiUtils.post(requestUrl, object);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSONObject.parseObject(result);
            if("200".equals(resultObject.getString("code"))){
                return "success";
            }
        }
        return "fail";
    }

    //同意提现
    public String agreeCashout (AgreeCashoutInVo agreeCashoutInVo){
        JSONObject object = new JSONObject();
        object.put("cashoutKid",agreeCashoutInVo.getCashoutKid());
        String requestUrl = apiHost + "/support/cashout/agreeCashout";
       // String requestUrl = "http://192.168.31.201:7150/support/cashout/agreeCashout";
        String result = ApiUtils.post(requestUrl, object);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSONObject.parseObject(result);
            if("200".equals(resultObject.getString("code"))){
                return "success";
            }
        }
        return "fail";
    }

    //删除提现
    public String deleteCashout (AgreeCashoutInVo agreeCashoutInVo){
        JSONObject object = new JSONObject();
        object.put("cashoutKid",agreeCashoutInVo.getCashoutKid());
        String requestUrl = apiHost + "/support/cashout/deleteCashout";
        // String requestUrl = "http://192.168.31.201:7150/support/cashout/deleteCashout";
        String result = ApiUtils.post(requestUrl, object);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSONObject.parseObject(result);
            if("200".equals(resultObject.getString("code"))){
                return "success";
            }
        }
        return "fail";
    }


}
