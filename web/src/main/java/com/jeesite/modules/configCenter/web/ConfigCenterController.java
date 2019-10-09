package com.jeesite.modules.configCenter.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.configCenter.entity.*;
import com.jeesite.modules.configCenter.service.ConfigCenterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.jeesite.common.web.BaseController.text;
import static com.jeesite.common.web.http.ServletUtils.renderResult;

@Controller
@RequestMapping(value = "${adminPath}/configCenter/configData")
@Slf4j
public class ConfigCenterController {
    @Autowired
    private ConfigCenterService configCenterService;

    /**
     * 查询列表
     */
   // @RequiresPermissions("test:testData:view")
    @RequestMapping(value = {"list", ""})
    public String list(ConfigCenterListVo configCenterListVo, Model model) {
        model.addAttribute("configCenterListVo", configCenterListVo);
        return "modules/configCenter/configCenterDataList";
    }

    /**
     * 查询列表数据
     */
   // @RequiresPermissions("test:testData:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<ConfigCenterListVo> listData(ConfigCenterListVo configCenterListVo, HttpServletRequest request, HttpServletResponse response) {
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");

        Page<ConfigCenterListVo> page = new Page<>();
        if(!StringUtils.isEmpty(pageSize)){
            page.setPageSize(Integer.parseInt(pageSize));
        }
        if(!StringUtils.isEmpty(pageNo)){
            page.setPageNo(Integer.parseInt(pageNo));
        }

        Map<String, Object> paramterMap = new HashMap<>();
        if(!StringUtils.isEmpty(request.getParameter("operationName"))){
            paramterMap.put("operationName", request.getParameter("operationName"));
        }
        if(!StringUtils.isEmpty(request.getParameter("configKey"))){
            paramterMap.put("configKey", request.getParameter("configKey"));
        }
        page.setOtherData(paramterMap);
        Page<ConfigCenterListVo> result = configCenterService.loadConfigCenterList(page);
        if(result != null){
            return result;
        }else {
            return new Page<ConfigCenterListVo>();
        }
    }


     /*
     * 跳转到添加配置页面
     */
   // @RequiresPermissions("test:testData:view")
    @RequestMapping(value = "toAdd")
    public String toAdd(AddConfigVo addConfigVo,Model model) {
        model.addAttribute("addConfigVo",addConfigVo);
        return "modules/configCenter/configCenterDataAdd";
    }

    /*
     * 添加配置
     */
   // @RequiresPermissions("test:testData:view")
    @RequestMapping(value = "add")
    @ResponseBody
    public String add(@Validated AddConfigVo addConfigVo) {
        log.info("添加配置入参:" + JSONObject.toJSONString(addConfigVo));
        configCenterService.addConfig(addConfigVo);
        return renderResult(Global.TRUE, text("添加配置成功！"));
    }

    /*
     * 跳转到修改配置页面
     */
  //  @RequiresPermissions("test:testData:view")
    @RequestMapping(value = "toUpdate")
    public String toUpdate (Model model,HttpServletRequest request){        //获取表单参数
        String configKey = request.getParameter("configKey");
        QueryConfigVo queryConfigVo = new QueryConfigVo();
        queryConfigVo.setConfigKey(configKey);
        ConfigEntity configEntity = configCenterService.queryConfig(queryConfigVo);
       /* String configValue = configEntity.getConfigValue();
        String replace = configValue.replace("\"", "\\\"");
        configEntity.setConfigValue(replace);*/
        model.addAttribute("configEntity",configEntity);
        return "modules/configCenter/configCenterDataUpdate";
    }


    /*
     * 修改配置
     */
   // @RequiresPermissions("test:testData:view")
    @RequestMapping(value = "update")
    @ResponseBody
    public String update(@Validated UpdateConfigVo updateConfigVo) {
        log.info("修改配置入参:" + JSONObject.toJSONString(updateConfigVo));
        configCenterService.updateConfig(updateConfigVo);
        return renderResult(Global.TRUE, text("修改配置成功！"));
    }
}
