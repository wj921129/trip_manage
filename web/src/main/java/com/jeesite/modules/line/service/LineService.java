/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.line.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.commom.utils.ApiPage;
import com.jeesite.modules.commom.utils.ApiResult;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.line.entity.AuditLinesInVo;
import com.jeesite.modules.line.entity.Line;
import com.jeesite.modules.line.entity.LineSearchOutVo;
import com.jeesite.modules.line.entity.SearchLineInVo;
import lombok.extern.slf4j.Slf4j;
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

    private static final String line = "http://192.168.31.201:7020";

    private static final String queryLineStatus = "/line/queryByParams";

    private static final String deleteLine = "/line/deleteLine";

    private static final String auditLines = "/line/auditLines";

    public ApiPage<LineSearchOutVo> queryAll(SearchLineInVo searchLineInVo){

        ApiResult<ApiPage<LineSearchOutVo>> page = ApiUtils.getPage(line+queryLineStatus, searchLineInVo, LineSearchOutVo.class);
        ApiPage<LineSearchOutVo> data = page.getData();

        return data;
    }

    public Line get(String kid){

        Line line = new Line();

        return line;
    }

    public void save(SearchLineInVo line){


        System.err.println("保存成功");
    }

    public void delete(String lineKid){

        String result = ApiUtils.post(line + deleteLine+"?lineKid="+lineKid, null);
        if (result != null){
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject != null){

                String code = jsonObject.getString("code");
                if ("200".equals(code) && "success".equals(jsonObject.getString("msg"))){

                    log.info("路线 : " + lineKid + "删除成功!");
                }else {

                    log.info(jsonObject.getString("errorMsg"));
                }
            }
        }
    }

    public void update(AuditLinesInVo auditLinesInVo){

        ApiUtils.post(line + auditLines, auditLinesInVo);

    }

}