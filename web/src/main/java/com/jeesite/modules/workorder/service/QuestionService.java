package com.jeesite.modules.workorder.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.service.TreeService;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.test.dao.TestTreeDao;
import com.jeesite.modules.test.entity.TestTree;
import com.jeesite.modules.workorder.dao.QuestionDao;
import com.jeesite.modules.workorder.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class QuestionService  extends TreeService<QuestionDao, Question> {

    @Value("${api.host}")
    private String apiHost;

    public List<Question> questionList(){
        List<Question> questions = new ArrayList<>();
        String result = ApiUtils.get(apiHost + "/workorder/question/queryQuestionList");
        //String result = ApiUtils.get("http://127.0.0.1:7400/workorder/question/queryQuestionList");
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if("200".equals(resultObject.getString("code"))){
                questions = (List<Question>)resultObject.get("data");
            }
        }
        return questions;
    }


    /*public List<Question> findList(Question question) {
        return super.findList(question);
    }*/
}
