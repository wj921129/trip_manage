package com.jeesite.modules.workorder.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.customerService.entity.UpdateComplainInVo;
import com.jeesite.modules.workorder.entity.Answer;
import com.jeesite.modules.workorder.entity.Form;
import com.jeesite.modules.workorder.entity.FormUpdateInVo;
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
        ob.put("status", 10);

        String requestUrl = apiHost + "/workorder/form/queryFormList";
        //String requestUrl = "http://127.0.0.1:7400/workorder/form/queryFormList";
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


    public String dealForm(FormUpdateInVo formUpdateInVo){
        String result = ApiUtils.post(apiHost + "/workorder/form/updateForm", formUpdateInVo);
        //String result = ApiUtils.post("http://127.0.0.1:7400/workorder/form/updateForm", formUpdateInVo);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject.getString("code");
        }
        return null;
    }


    public JSONObject formFile(String kid){
        JSONObject obj = new JSONObject();
        JSONArray imageArr = new JSONArray();
        JSONArray fileArr = new JSONArray();
        String result = ApiUtils.get(apiHost + "/workorder/form/queryByKid?kid="+kid);
        //String result = ApiUtils.get("http://127.0.0.1:7400/workorder/form/queryByKid?kid="+kid);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if("200".equals(resultObject.getString("code"))){
                imageArr = resultObject.getJSONObject("data").getJSONArray("imageOutVos");
                fileArr = resultObject.getJSONObject("data").getJSONArray("fileOutVos");
            }
        }
        obj.put("imageArr", imageArr);
        obj.put("fileArr", fileArr);
        return obj;
    }

}
