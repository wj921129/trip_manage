package com.jeesite.modules.workorder.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.workorder.entity.Answer;
import com.jeesite.modules.workorder.entity.AnswerUpdateInVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class AnswerService {

    @Value("${api.host}")
    private String apiHost;

    public Page<Answer> queryAllAnswer(Page<Answer> page){

        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());

        String requestUrl = apiHost + "/workorder/answer/queryAnswerList";
        //String requestUrl = "http://127.0.0.1:7400/workorder/answer/queryAnswerList";
        String result = ApiUtils.get(requestUrl,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<Answer> answers = (List<Answer>)resultObject.getJSONObject("data").get("entities");

                    page.setList(answers);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));

                }
            }
        }

        return page;
    }


    public String updateAnswer(AnswerUpdateInVo answerUpdateInVo){
        String result = ApiUtils.post(apiHost + "/workorder/answer/updateAnswer", answerUpdateInVo);
        //String result = ApiUtils.post("http://127.0.0.1:7400/workorder/answer/updateAnswer", answerUpdateInVo);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject.getString("code");
        }
        return null;
    }


    public String deleteAnswer(String kid){
        String result = ApiUtils.post(apiHost + "/workorder/answer/deleteAnswer?kid="+kid, null);
        //String result = ApiUtils.post("http://127.0.0.1:7400/workorder/answer/deleteAnswer?kid="+kid, null);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject.getString("code");
        }
        return null;
    }


}
