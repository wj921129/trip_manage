package com.jeesite.modules.customerService.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.customerService.entity.LineReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class LineReportService {

    @Value("${api.host}")
    private String apiHost;

    public Page<LineReport> queryLineReport(Page<LineReport> page){

        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());

        String requestUrl = apiHost + "/support/lineReport/queryByPage";
        //String requestUrl = "http://localhost:6150/support/lineReport/queryByPage";
        String result = ApiUtils.get(requestUrl,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<LineReport> lineReports = (List<LineReport>) resultObject.getJSONObject("data").get("entities");

                    page.setList(lineReports);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));

                }
            }
        }

        return page;

    }



    public JSONObject lineReportData(String lineKid){
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject ob = new JSONObject();
        ob.put("lineKid", lineKid);
        String requestUrl = apiHost + "/support/lineReport/queryByLineKid";
        //String requestUrl = "http://localhost:6150/support/lineReport/queryByLineKid";
        String requestResult = ApiUtils.get(requestUrl,ob);
        if(!StringUtils.isEmpty(requestResult)){
            JSONObject resultObject = JSON.parseObject(requestResult);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    array = resultObject.getJSONArray("data");

                }
            }
        }

        result.put("tableData", array);

        return result;
    }



}