package com.jeesite.modules.customerService.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.customerService.entity.Complain;
import com.jeesite.modules.customerService.entity.UpdateComplainInVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ComplainService {

    @Value("${api.host}")
    private String apiHost;

    public Page<Complain> queryAllComplain(Page<Complain> page){
        Map<String, Object> otherData = page.getOtherData();

        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());
        ob.put("complainStatu", otherData.get("complainStatu"));
        ob.put("complainReason", otherData.get("complainReason"));

        String requestUrl = apiHost + "/support/complain/queryComplain";
        //String requestUrl = "http://192.168.31.198:6150/support/complain/queryComplain";
        String result = ApiUtils.get(requestUrl,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<Complain> reports = (List<Complain>)resultObject.getJSONObject("data").get("entities");

                    page.setList(reports);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));

                }
            }
        }

        return page;
    }



    public String updateComplain(UpdateComplainInVo updateComplainInVo){
        String result = ApiUtils.post(apiHost + "/support/complain/updateComplain", updateComplainInVo);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject.getString("code");
        }
        return null;
    }


}
