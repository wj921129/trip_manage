package com.jeesite.modules.auth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.auth.entity.AuditEnterpriseAuthInVo;
import com.jeesite.modules.auth.entity.AuditIdAuthInVo;
import com.jeesite.modules.auth.entity.EnterpriseAuth;
import com.jeesite.modules.auth.entity.IdAuth;
import com.jeesite.modules.commom.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class AuthService {

    @Value("${api.host}")
    private String apiHost;

    public Page<IdAuth> queryIdAuthInfo(Page<IdAuth> page){
        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());
        ob.put("idAuth", 20);
        String requestUrl = apiHost + "/user/baseInfo/idAuthInfoList";
        //String requestUrl = "http://192.168.31.198:6100/user/baseInfo/idAuthInfoList";
        String result = ApiUtils.post(requestUrl,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<IdAuth> idAuths = (List<IdAuth>)resultObject.getJSONObject("data").get("entities");

                    page.setList(idAuths);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));

                }
            }
        }

        return page;
    }

    public Page<EnterpriseAuth> queryEnterpriseAuthInfo(Page<EnterpriseAuth> page){
        JSONObject ob = new JSONObject();
        ob.put("pageSize", page.getPageSize());
        ob.put("pageNumber", page.getPageNo());
        ob.put("enterpriseAuth", 20);
        String requestUrl = apiHost + "/user/baseInfo/enterpriseAuthInfoList";
        //String requestUrl = "http://192.168.31.198:6100/user/baseInfo/enterpriseAuthInfoList";
        String result = ApiUtils.post(requestUrl,ob);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            if(resultObject != null){
                if("200".equals(resultObject.getString("code"))){
                    List<EnterpriseAuth> enterpriseAuths = (List<EnterpriseAuth>)resultObject.getJSONObject("data").get("entities");

                    page.setList(enterpriseAuths);
                    page.setCount(resultObject.getJSONObject("data").getLong("count"));

                }
            }
        }
        return page;
    }



    public String auditIdAuth(AuditIdAuthInVo auditIdAuthInVo){
        String result = ApiUtils.post(apiHost + "/user/baseInfo/auditIdAuth", auditIdAuthInVo);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject.getString("code");
        }
        return null;
    }


    public String auditEnterpriseAuth(AuditEnterpriseAuthInVo auditEnterpriseAuthInVo){
        String result = ApiUtils.post(apiHost + "/user/baseInfo/auditEnterpriseAuth", auditEnterpriseAuthInVo);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject.getString("code");
        }
        return null;
    }


}
