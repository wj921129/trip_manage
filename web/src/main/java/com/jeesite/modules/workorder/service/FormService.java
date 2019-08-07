package com.jeesite.modules.workorder.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.workorder.entity.Answer;
import com.jeesite.modules.workorder.entity.Form;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class FormService {

    @Value("${api.host}")
    private String apiHost;

    public Page<Form> queryAllForm(Page<Form> page){

        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());

        //String requestUrl = apiHost + "/workorder/form/queryFormList";
        String requestUrl = "http://127.0.0.1:7400/workorder/form/queryFormList";
        String result = ApiUtils.get(requestUrl,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<Form> forms = (List<Form>)resultObject.getJSONObject("data").get("entities");

                    page.setList(forms);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));

                }
            }
        }

        return page;
    }
}
