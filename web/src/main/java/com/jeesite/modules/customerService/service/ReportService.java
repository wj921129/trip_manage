package com.jeesite.modules.customerService.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.customerService.entity.Report;
import com.jeesite.modules.dynamic.entity.DynamicByKidOutVo;
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

    private static final String queryReport = "/support/report/queryReport";

    private static final String queryByKid = "/dynamic/queryByKid";

    public Page<Report> queryAllReport(Page<Report> page){
        Map<String, Object> otherData = page.getOtherData();

        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());
        ob.put("reportReason", otherData.get("reportReason"));
        ob.put("discoverKid", otherData.get("discoverKid"));

        String result = ApiUtils.get(apiHost + queryReport,ob);
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



    public DynamicByKidOutVo showDynamic(String discoverKid){
        DynamicByKidOutVo outVo = new DynamicByKidOutVo();
        JSONObject ob = new JSONObject();
        ob.put("kid", discoverKid);
        String result = ApiUtils.get(apiHost + queryByKid,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    JSONObject data = resultObject.getJSONObject("data");
                    outVo = JSONObject.toJavaObject(data, DynamicByKidOutVo.class);
                    outVo.setIfExist("true");
                }
            }
        }
        return outVo;

    }


}
