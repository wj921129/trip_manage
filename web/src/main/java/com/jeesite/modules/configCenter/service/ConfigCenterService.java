package com.jeesite.modules.configCenter.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.configCenter.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ConfigCenterService {
    @Value("${api.host}")
    private String apiHost;

    //加载configCenter列表
    public Page<ConfigCenterListVo> loadConfigCenterList(Page<ConfigCenterListVo> page){
        //构建入参对象
        JSONObject object = new JSONObject();
        object.put("pageSize",page.getPageSize());
        object.put("pageNumber",page.getPageNo());
        object.put("configKey",page.getOtherData().get("configKey"));
        object.put("operationName",page.getOtherData().get("operationName"));

        String requestUrl = apiHost + "/support/config/loadConfigList";
        String result = ApiUtils.post(requestUrl, object);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<ConfigCenterListVo> configCenterListVoList = (List<ConfigCenterListVo>)resultObject.getJSONObject("data").get("entities");
                    page.setList(configCenterListVoList);
                   String s = resultObject.getJSONObject("data").toJSONString();
                    if(!s.equals("{}")){
                        page.setCount(resultObject.getJSONObject("data").getLong("count"));
                    }else {
                        page.setCount(0);
                    }
                }
                return page;
            }
        }
        return null ;
    }

    //添加配置
    public void addConfig (AddConfigVo addConfigVo){
        String requestUrl = apiHost + "/support/config/addConfig";
        String result = ApiUtils.post(requestUrl, addConfigVo);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                 log.info("添加配置成功");
                }
            }
        }
    }

    //查询配置
    public ConfigEntity queryConfig (QueryConfigVo queryConfigVo){
        String requestUrl = apiHost + "/support/config/queryConfigByConfigKid";
        String result = ApiUtils.post(requestUrl, queryConfigVo);
        if(!StringUtils.isEmpty(result)) {
            JSONObject resultObject = JSON.parseObject(result);
            if (resultObject != null) {
                if ("200".equals(resultObject.getString("code"))) {
                    log.info("查询配置对象成功");
                    ConfigEntity configEntity = resultObject.getObject("data", ConfigEntity.class);
                    return configEntity;
                }
            }
        }
        return null;
    }

    //修改配置
    public void updateConfig (UpdateConfigVo updateConfigVo){
        String requestUrl = apiHost + "/support/config/updateConfig";
        String result = ApiUtils.post(requestUrl, updateConfigVo);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    log.info("修改配置成功");
                }
            }
        }
    }

}
