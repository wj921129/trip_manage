package com.jeesite.modules.dynamic.service;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.commom.utils.ApiPage;
import com.jeesite.modules.commom.utils.ApiResult;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.dynamic.entity.Dynamic;
import com.jeesite.modules.dynamic.entity.DynamicInVo;
import com.jeesite.modules.dynamic.entity.DynamicOutVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly=true)
public class DynamicService {

    @Value("${api.201host}")
    private String apiHost;

//    private static final String apiHost = "http://192.168.31.40:7130";

    private static final String queryParams = "/dynamic/queryByParams";

    private static final String editDynamic = "/dynamic/editDynamic";

    public ApiPage<DynamicOutVo> queryParams(DynamicInVo searchLineInVo){

        ApiResult<ApiPage<DynamicOutVo>> page = ApiUtils.getPage(apiHost+queryParams, searchLineInVo, DynamicOutVo.class);
        ApiPage<DynamicOutVo> data = page.getData();

        return data;
    }

    public String edit(DynamicInVo dynamicInVo){

        Dynamic dynamic = new Dynamic();
        dynamic.setDelFlag(dynamicInVo.getDelFlag());
        dynamic.setKid(dynamicInVo.getDynamicKid());
        dynamic.setDiscoverRemarks(dynamicInVo.getDiscoverRemarks());
        dynamic.setUserKid(dynamicInVo.getUserKid());

        String message = "";

        String result = ApiUtils.post(apiHost + editDynamic, JSONObject.toJSON(dynamic));
        if (result != null){
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject != null){

                String code = jsonObject.getString("code");
                if ("200".equals(code) && "success".equals(jsonObject.getString("msg"))){

                    message = "操作成功!";
                    log.info("路线 : " + dynamicInVo.getDynamicKid() + "删除成功!");
                }else {
                    if (jsonObject.getString("errorMsg") != null){
                        message = jsonObject.getString("errorMsg");
                    }
                    log.info(jsonObject.getString("errorMsg"));
                }
            }
        }
        log.info(result);

        return message;
    }


}
