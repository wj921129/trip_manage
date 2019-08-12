package com.jeesite.modules.user.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.user.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserInfoService {

    @Value("${api.host}")
    private String apiHost;

    private static final String queryAllUser = "/user/baseInfo/queryAllUser";

    private static final String queryCount = "/support/userStatistics/queryCount";

    public Page<UserInfo> queryAllUser(Page<UserInfo> page){
        Map<String, Object> otherData = page.getOtherData();

        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());
        ob.put("phone", otherData.get("phone"));

        String result = ApiUtils.get(apiHost + queryAllUser,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<UserInfo> userInfos = (List<UserInfo>)resultObject.getJSONObject("data").get("entities");

                    page.setList(userInfos);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));

                 }
            }
        }

        return page;
    }



    public JSONObject echartsData(int countValue){
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        int newRegisterCount = 0;
        int dailyVisitCount = 0;
        int totalRegisterCount = 0;
        result.put("echartsData", new JSONArray());
        JSONObject ob = new JSONObject();
        ob.put("countValue", countValue);
        ob.put("pageSize", 30);
        JSONObject jsonObject = getResult(ob);
        if(jsonObject != null){
            JSONObject temp = jsonObject.getJSONObject("data");
            if(temp.getJSONObject("detailOutVos") != null){
                array = temp.getJSONObject("detailOutVos").getJSONArray("entities");
            }
            newRegisterCount = temp.getInteger("newRegisterCount");
            dailyVisitCount = temp.getInteger("dailyVisitCount");
            totalRegisterCount = temp.getInteger("totalRegisterCount");

        }

        result.put("echartsData", array);
        result.put("newRegisterCount", newRegisterCount);
        result.put("dailyVisitCount", dailyVisitCount);
        result.put("totalRegisterCount", totalRegisterCount);

        return result;
    }


    public JSONObject tableData(int countValue, int pageNo, int pageSize){
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        int total = 0;
        JSONObject ob = new JSONObject();
        ob.put("countValue", countValue);
        ob.put("pageNumber", pageNo);
        ob.put("pageSize", pageSize);
        JSONObject jsonObject = getResult(ob);
        if(jsonObject != null){
            JSONObject temp = jsonObject.getJSONObject("data");
            if(temp.getJSONObject("detailOutVos") != null){
                array = temp.getJSONObject("detailOutVos").getJSONArray("entities");
                total = temp.getJSONObject("detailOutVos").getInteger("count");
            }
        }

        result.put("tableData", array);
        result.put("total", total);

        return result;
    }


    public JSONObject tableDataByTime(String fromDay, String toDay, int pageNo, int pageSize){
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        int total = 0;
        JSONObject ob = new JSONObject();
        if(!StringUtils.isEmpty(fromDay)){
            ob.put("fromDay",fromDay);
        }
        if(!StringUtils.isEmpty(toDay)){
            ob.put("toDay",toDay);
        }
        ob.put("pageNumber", pageNo);
        ob.put("pageSize", pageSize);
        JSONObject jsonObject = getResult(ob);
        if(jsonObject != null){
            JSONObject temp = jsonObject.getJSONObject("data");
            if(temp.getJSONObject("detailOutVos") != null){
                array = temp.getJSONObject("detailOutVos").getJSONArray("entities");
                total = temp.getJSONObject("detailOutVos").getInteger("count");
            }
        }

        result.put("tableData", array);
        result.put("total", total);

        return result;
    }


    private JSONObject getResult(JSONObject paramter){
        String result = ApiUtils.post(apiHost + queryCount, paramter);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    return resultObject;
                }
            }
        }
        return null;
    }


}
