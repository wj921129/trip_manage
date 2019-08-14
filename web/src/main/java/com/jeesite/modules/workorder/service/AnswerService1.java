package com.jeesite.modules.workorder.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.workorder.entity.Answer1;
import com.jeesite.modules.workorder.entity.AnswerUpdateInVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class AnswerService1 {

    @Value("${api.host}")
    private String apiHost;
    //private String apiHost = "http://127.0.0.1:7400";

    private static final String queryAnswerList = "/workorder/answer/queryAnswerList";

    private static final String updateAnswer = "/workorder/answer/updateAnswer";

    private static final String deleteAnswer = "/workorder/answer/deleteAnswer";

    public Page<Answer1> queryAllAnswer(Page<Answer1> page){

        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());

        String result = ApiUtils.get(apiHost + queryAnswerList,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<Answer1> answers = (List<Answer1>)resultObject.getJSONObject("data").get("entities");

                    page.setList(answers);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));

                }
            }
        }

        return page;
    }


    public String updateAnswer(AnswerUpdateInVo answerUpdateInVo){
        String result = ApiUtils.post(apiHost + updateAnswer, answerUpdateInVo);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject.getString("code");
        }
        return null;
    }


    public String deleteAnswer(String kid){
        String result = ApiUtils.post(apiHost + deleteAnswer + "?kid="+kid, null);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject.getString("code");
        }
        return null;
    }


}