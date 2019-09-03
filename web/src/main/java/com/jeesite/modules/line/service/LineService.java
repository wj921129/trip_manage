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
    @Value("${api.host}")
    private String apiHost;
//    private String apiHost = "http://192.168.31.40:7110";

//    private static final String queryByParams = "/line/queryByParams";
    private static final String queryByParams = "/line/newLine/queryByParams";

//    private static final String deleteLine = "/line/deleteLine";
    private static final String deleteLine = "/line/newLine/deleteLine";

//    private static final String cancelLine = "/line/cancelLine";
    private static final String cancelLine = "/line/newLine/cancelLine";

//    private static final String auditLines = "/line/auditLines";
    private static final String auditLines = "/line/newLine/auditLines";

//    private static final String setLineToHome = "/line/setLineToHome";
    private static final String setLineToHome = "/line/newLine/setLineToHome";

//    private static final String querySchedule = "/line/querySchedule";
    private static final String queryNewSchedule = "/line/newLine/querySchedule";

    private static final String updateLineStatus = "/line/newLine/updateLineStatus";


    public ApiPage<LineSearchOutVo> queryAll(SearchLineInVo searchLineInVo){

        ApiResult<ApiPage<LineSearchOutVo>> page = ApiUtils.getPage(apiHost+queryByParams, searchLineInVo, LineSearchOutVo.class);
        ApiPage<LineSearchOutVo> data = page.getData();

        return data;
    }

    public NewLineDetailsOutVo querySchedule(String lineKid, String userKid){

        NewLineDetailsOutVo lineDetailsOutVo = new NewLineDetailsOutVo();

        String result = ApiUtils.get(apiHost + queryNewSchedule + "?lineKid=" + lineKid + "&userKid=" + userKid);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject != null){

            String code = jsonObject.getString("code");
            if ("200".equals(code) && "success".equals(jsonObject.getString("msg"))){
                lineDetailsOutVo = jsonObject.getObject("data", NewLineDetailsOutVo.class);
            }else {
                if (jsonObject.getString("errorMsg") != null){
                }
                log.info(jsonObject.getString("errorMsg"));
            }
        }

        return lineDetailsOutVo;

    }

    public Line get(String kid){

        Line line = new Line();

        return line;
    }

    public void save(SearchLineInVo line){


        System.err.println("保存成功");
    }

    public String upperOrLower(String lineKid, Integer flag, String remarks){

        log.info("apiHost : " + apiHost);

        JSONObject data = new JSONObject();
        String result = "";
        String message = "";
        data.put("kid", lineKid);

        if (flag == 1){ // 上架
            data.put("lineStatu", 20);
            result = ApiUtils.post(apiHost + cancelLine, data);
        }else if (flag == 2){ // 下架
            data.put("lineStatu", 30);
            result = ApiUtils.post(apiHost + cancelLine, data);
        }else if (flag == 4){ // 删除
            result = ApiUtils.post(apiHost + deleteLine + "?lineKid=" + lineKid, null);
        }else if (flag == 5){ // 强制下架
            data.put("lineStatu", 60);
            data.put("remarks", remarks);
            result = ApiUtils.post(apiHost + updateLineStatus, data);
        }

        if (result != null){
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject != null){

                String code = jsonObject.getString("code");
                if ("200".equals(code) && "success".equals(jsonObject.getString("msg"))){
                    message = "操作成功!";
                    log.info("路线 : " + lineKid + "操作成功!");
                }else {
                    if (jsonObject.getString("errorMsg") != null){
                        message = jsonObject.getString("errorMsg");
                    }
                    log.info(jsonObject.getString("errorMsg"));
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