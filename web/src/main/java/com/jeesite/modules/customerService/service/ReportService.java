package com.jeesite.modules.customerService.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.customerService.entity.Report;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportService {

    @Value("${api.host}")
    private String apiHost;

    public Page<Report> queryAllReport(Page<Report> page){
        Map<String, Object> otherData = page.getOtherData();

        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());
        ob.put("reportReason", otherData.get("reportReason"));
        ob.put("discoverKid", otherData.get("discoverKid"));

        //String requestUrl = apiHost + "/support/report/queryReport";
        String requestUrl = "http://192.168.31.198:6150/support/report/queryReport";
        String result = ApiUtils.get(requestUrl,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<Report> reports = (List<Report>)resultObject.getJSONObject("data").get("entities");

                    page.setList(reports);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));

                }
            }
        }

        return page;
    }

}
