package com.jeesite.modules.advert.service;


import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.advert.entity.AdtInVo;
import com.jeesite.modules.advert.entity.AdtOutVo;
import com.jeesite.modules.commom.utils.ApiPage;
import com.jeesite.modules.commom.utils.ApiResult;
import com.jeesite.modules.commom.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly=true)
public class AdtService {

    @Value("${api.host}")
    private String apiHost;


    private static final String  getAdts = "/support/adt/getAdts";

    private static final String  editAdt = "/support/adt/editAdt";


    public ApiPage<AdtOutVo> getAdts(AdtInVo adtInVo){

        ApiResult<ApiPage<AdtOutVo>> page = ApiUtils.getPage(apiHost+getAdts, adtInVo, AdtOutVo.class);
        ApiPage<AdtOutVo> data = page.getData();

        return data;
    }

    public String editAdt(String kid, Integer adtStatus){

        log.info("apiHost : " + apiHost);

        JSONObject data = new JSONObject();
        String result = "";
        String message = "";
        data.put("kid", kid);
        data.put("adtStatus", adtStatus);

        result = ApiUtils.post(apiHost + editAdt, data);

        if (result != null){
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject != null){

                String code = jsonObject.getString("code");
                if ("200".equals(code) && "success".equals(jsonObject.getString("msg"))){
                    message = "操作成功!";
                    log.info("广告 : " + kid + "操作成功!");
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
}
