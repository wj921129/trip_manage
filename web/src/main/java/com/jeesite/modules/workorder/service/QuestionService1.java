package com.jeesite.modules.workorder.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.workorder.entity.Question1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService1{

    @Value("${api.host}")
    private String apiHost;

    private static final String queryAnswerList = "/workorder/question/queryQuestionList";

    public List<Question1> questionList(){
        List<Question1> questions = new ArrayList<>();
        String result = ApiUtils.get(apiHost + queryAnswerList);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if("200".equals(resultObject.getString("code"))){
                questions = (List<Question1>)resultObject.get("data");
            }
        }
        return questions;
    }
}
