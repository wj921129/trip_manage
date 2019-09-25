package com.jeesite.modules.workorder.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.workorder.entity.Form;
import com.jeesite.modules.workorder.entity.FormByKidOutVo;
import com.jeesite.modules.workorder.entity.FormReplyAddInVo;
import com.jeesite.modules.workorder.entity.FormUpdateInVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FormService {

    @Value("${api.host}")
    private String apiHost;
    //private String apiHost = "http://127.0.0.1:7400";

    private static final String queryFormList = "/workorder/form/queryFormList";

    private static final String queryByKid = "/workorder/form/queryByKid";

    private static final String addReply = "/workorder/form/addReply";

    private static final String updateForm = "/workorder/form/updateForm";

    public Page<Form> queryAllForm(Page<Form> page){
        Map<String, Object> otherData = page.getOtherData();

        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());
        ob.put("status", otherData.get("status"));

        String result = ApiUtils.get(apiHost + queryFormList,ob);
        //String result = ApiUtils.get("http://127.0.0.1:7400" + queryFormList,ob);
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


    public FormByKidOutVo queryByKid(String kid){
        FormByKidOutVo formByKidOutVo = new FormByKidOutVo();
        String result = ApiUtils.get(apiHost + queryByKid + "?kid=" + kid);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    formByKidOutVo = resultObject.getObject("data", FormByKidOutVo.class);
                }
            }
        }

        return formByKidOutVo;
    }


    public String addReply(FormReplyAddInVo formReplyAddInVo){
        String result = ApiUtils.post(apiHost + addReply, formReplyAddInVo);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject.getString("code");
        }
        return null;
    }


    public String doneDeal(FormUpdateInVo formUpdateInVo){
        String result = ApiUtils.post(apiHost + updateForm, formUpdateInVo);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject.getString("code");
        }
        return null;
    }


}
