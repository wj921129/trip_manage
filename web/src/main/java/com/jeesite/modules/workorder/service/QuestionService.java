package com.jeesite.modules.workorder.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.workorder.entity.FormUpdateInVo;
import com.jeesite.modules.workorder.entity.Question;
import com.jeesite.modules.workorder.entity.TreeQuestionOutVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Value("${api.host}")
    private String apiHost;

    private static final String queryAnswerList = "/workorder/question/queryQuestionList";

    private static final String queryTreeQuestion = "/workorder/question/queryTreeQuestion";

    private static final String deleteQuestion = "/workorder/question/deleteQuestion";

    private static final String addQuestion = "/workorder/question/addQuestion";

    private static final String updateQuestion = "/workorder/question/updateQuestion";

    public List<Question> questionList(){
        List<Question> questions = new ArrayList<>();
        String result = ApiUtils.get(apiHost + queryAnswerList);
        //String result = ApiUtils.get("http://127.0.0.1:7400" + queryAnswerList);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if("200".equals(resultObject.getString("code"))){
                questions = (List<Question>)resultObject.get("data");
            }
        }
        return questions;
    }


    public List<TreeQuestionOutVo> queryTreeQuestion(){
        List<TreeQuestionOutVo> treeQuestionOutVos = new ArrayList<>();
        String result = ApiUtils.get(apiHost + queryTreeQuestion);
        //String result = ApiUtils.get("http://127.0.0.1:7400" + queryTreeQuestion);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if("200".equals(resultObject.getString("code"))){
                treeQuestionOutVos = (List<TreeQuestionOutVo>)resultObject.get("data");
            }
        }
        return treeQuestionOutVos;
    }


    public JSONObject deleteQuestion(String kid){
        JSONObject returnObj = new JSONObject();
        returnObj.put("result", "failed");
        String result = ApiUtils.post(apiHost + deleteQuestion + "?kid="+kid,null);
        //String result = ApiUtils.post("http://127.0.0.1:7400" + deleteQuestion + "?kid="+kid,null);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if("200".equals(resultObject.getString("code"))){
                returnObj.put("result", "success");
            }
        }
        return returnObj;
    }


    public JSONObject save(Question question){
        String result = "";
        if(StringUtils.isEmpty(question.getKid())){//新增
            result = ApiUtils.post(apiHost + addQuestion, question);
            //result = ApiUtils.post("http://127.0.0.1:7400" + addQuestion,question);
        }else{//修改
            result = ApiUtils.post(apiHost + updateQuestion, question);
            //result = ApiUtils.post("http://127.0.0.1:7400" + updateQuestion,question);
        }

        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject;
        }
        return null;
    }


}
