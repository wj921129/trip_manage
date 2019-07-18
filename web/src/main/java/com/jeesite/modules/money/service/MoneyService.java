/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.money.service;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.money.entity.MoneyCountOutVo;
import com.jeesite.modules.money.entity.MoneyInVo;
import com.jeesite.modules.money.entity.MoneyStatisticsInVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试树表Service
 * @author ThinkGem
 * @version 2018-04-22
 */
@Slf4j
@Service
@Transactional(readOnly=true)
public class MoneyService {


    @Value("${api.201host}")
    private String apiHost;

//    private static final String apiHost = "http://192.168.31.40:7150";

    private static final String queryPayCount = "/support/payStatistics/queryPayCount";



    public MoneyCountOutVo queryParams(MoneyStatisticsInVo statisticsInVo){

        MoneyInVo moneyInVo = new MoneyInVo();
        moneyInVo.setCountValue(statisticsInVo.getCountValue());
        moneyInVo.setFromDay(statisticsInVo.getFromDay());
        if (statisticsInVo.getPageSize() == null){
            moneyInVo.setPageSize(30);
        }else {
            moneyInVo.setPageSize(statisticsInVo.getPageSize());
        }
        moneyInVo.setPageNumber(statisticsInVo.getPageNo());

        moneyInVo.setToDay(statisticsInVo.getToDay());

        String result = ApiUtils.post(apiHost + queryPayCount, moneyInVo);
        if (result != null){
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject != null){
                String code = jsonObject.getString("code");
                if ("200".equals(code) && "success".equals(jsonObject.getString("msg"))){
                    String data = jsonObject.getString("data");
                    MoneyCountOutVo moneyCountOutVo = JSONObject.parseObject(data, MoneyCountOutVo.class);

                    return moneyCountOutVo;
                }
                log.info(jsonObject.getString("errorMsg"));
            }
        }
        return null;
    }

}