package com.jeesite.modules.user.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.user.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserInfoService {

    @Value("${api.host}")
    private String apiHost;

    public Page<UserInfo> queryAllUser(Page<UserInfo> page){
        Map<String, Object> otherData = page.getOtherData();

        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());
        ob.put("phone", otherData.get("phone"));

        String requestUrl = apiHost + "/user/baseInfo/queryAllUser";
        String result = ApiUtils.get(requestUrl,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<UserInfo> userInfos = (List<UserInfo>)resultObject.getJSONObject("data").get("entities");

                    page.setList(userInfos);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));

                 }
            }
        }

        return page;
    }


}
