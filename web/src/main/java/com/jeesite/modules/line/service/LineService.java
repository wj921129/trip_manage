/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.line.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.commom.utils.ApiPage;
import com.jeesite.modules.commom.utils.ApiResult;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.line.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 测试树表Service
 * @author ThinkGem
 * @version 2018-04-22
 */
@Slf4j
@Service
@Transactional(readOnly=true)
public class LineService {
//
//    @Value("${api.201host}")
//    private String apiHost;
    private String apiHost = "http://192.168.31.40:7110";

    private static final String queryByParams = "/line/queryByParams";

    private static final String deleteLine = "/line/deleteLine";

    private static final String cancelLine = "/line/cancelLine";

    private static final String auditLines = "/line/auditLines";

    private static final String setLineToHome = "/line/setLineToHome";

    private static final String querySchedule = "/line/querySchedule";

    public ApiPage<LineSearchOutVo> queryAll(SearchLineInVo searchLineInVo){

        ApiResult<ApiPage<LineSearchOutVo>> page = ApiUtils.getPage(apiHost+queryByParams, searchLineInVo, LineSearchOutVo.class);
        ApiPage<LineSearchOutVo> data = page.getData();

        return data;
    }

    public ScheduleOutVo querySchedule(String lineKid, String userKid){

//        ApiResult<ScheduleOutVo> single = ApiUtils.getSingle(apiHost + querySchedule + "?lineKid=" + lineKid + "&userKid=" + userKid, ScheduleOutVo.class);
//        ScheduleOutVo data = single.getData();
        ScheduleOutVo scheduleOutVo = new ScheduleOutVo();

        String result = ApiUtils.get(apiHost + querySchedule + "?lineKid=" + lineKid + "&userKid=" + userKid);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject != null){

            String code = jsonObject.getString("code");
            if ("200".equals(code) && "success".equals(jsonObject.getString("msg"))){
                scheduleOutVo = jsonObject.getObject("data", ScheduleOutVo.class);
            }else {
                if (jsonObject.getString("errorMsg") != null){
                }
                log.info(jsonObject.getString("errorMsg"));
            }
        }

        return scheduleOutVo;

    }

    public Line get(String kid){

        Line line = new Line();

        return line;
    }

    public void save(SearchLineInVo line){


        System.err.println("保存成功");
    }

    public String delete(String lineKid, Integer flag){

        String message = "";

        if (flag == 0){ // 取消
            String result = ApiUtils.post(apiHost + cancelLine+"?lineKid="+lineKid, null);
            if (result != null){
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject != null){

                    String code = jsonObject.getString("code");
                    if ("200".equals(code) && "success".equals(jsonObject.getString("msg"))){

                        message = "操作成功!";
                        log.info("路线 : " + lineKid + "删除成功!");
                    }else {
                        if (jsonObject.getString("errorMsg") != null){
                            message = jsonObject.getString("errorMsg");
                        }
                        log.info(jsonObject.getString("errorMsg"));
                    }
                }
            }
            log.info(result);
        }else if (flag == 1){ //删除

            String result = ApiUtils.post(apiHost + deleteLine+"?lineKid="+lineKid, null);
            if (result != null){
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject != null){

                    String code = jsonObject.getString("code");
                    if ("200".equals(code) && "success".equals(jsonObject.getString("msg"))){
                        message = "操作成功!";
                        log.info("路线 : " + lineKid + "删除成功!");
                    }else {
                        if (jsonObject.getString("errorMsg") != null){
                            message = jsonObject.getString("errorMsg");
                        }
                        log.info(jsonObject.getString("errorMsg"));
                    }
                }
            }
        }

        return message;
    }

    public void update(AuditLinesInVo auditLinesInVo){

        ApiUtils.post(apiHost + auditLines, auditLinesInVo);

    }

    public void setLineToHome(LineDisplayInVo lineDisplayInVo){

        ApiUtils.post(apiHost + setLineToHome, lineDisplayInVo);
    }

}