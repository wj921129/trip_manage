package com.jeesite.modules.order.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.commom.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class OrderStatisticsService {
    @Value("${api.host}")
    private String apiHost;

    //加载订单统计表格数据
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
            array = temp.getJSONArray("entities");
            total = temp.getInteger("count");
        }

        result.put("tableData", array);
        result.put("total", total);

        return result;
    }

    //加载订单统计图数据
    public JSONObject echartsData(int countValue){
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.put("echartsData", new JSONArray());
        JSONObject ob = new JSONObject();
        ob.put("countValue", countValue);
        ob.put("pageSize", 30);
        JSONObject jsonObject = getResult(ob);
        if(jsonObject != null){
            JSONObject temp = jsonObject.getJSONObject("data");
            array = temp.getJSONArray("entities");
        }
        result.put("echartsData", array);
        return result;
    }

    //根据起始时间查询表格数据
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
            array = temp.getJSONArray("entities");
            total = temp.getInteger("count");
        }
        result.put("tableData", array);
        result.put("total", total);
        return result;
    }

    //访问trip后台接口公共方法
    private JSONObject getResult(JSONObject paramter){
        //String requestUrl = apiHost + "/support/statistics/order/queryOrderCount";
        String requestUrl = "http://192.168.31.201:7150/support/statistics/order/queryOrderCount";
        String result = ApiUtils.post(requestUrl, paramter);
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
