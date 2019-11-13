/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.monitor.web;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.commom.utils.ApiPage;
import com.jeesite.modules.monitor.entity.AppOut;
import com.jeesite.modules.monitor.entity.AppIn;
import com.jeesite.modules.monitor.entity.vo.SystemInfo;
import com.jeesite.modules.monitor.service.AppService;
import com.jeesite.modules.think.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "${adminPath}/app")
public class AppController extends BaseController {

    @Autowired
    private AppService appService;

    /**
     * 查看编辑服务表单
     * @param appIn
     * @param model
     * @return
     */
    @GetMapping(value = "form")
    public String form(AppIn appIn, Model model) {
        model.addAttribute("appIn",appIn);
        return "modules/monitor/monitorDataForm";
    }

    /**
     * 创建编辑监控服务
     */
//	@RequiresPermissions("qianying:app")
//    @PostMapping(value = "create")
    @RequestMapping(value = "create")
    @ResponseBody
    public String create(AppIn appIn ) {

        log.info("创建或编辑服务的名称，appName :" + appIn.getAppName() + " 中文名称 :" + appIn.getName() + " 服务状态+ :" + appIn.getAppStatus() + " 启动命令 :" +
                appIn.getAppStart());
        if (appIn.getAppName() == null || StringUtils.isEmpty(appIn.getAppName())){
            return renderResult(Global.FALSE, text("操作失败,服务名称不能为空！"));
        }else if(appIn.getName() == null || StringUtils.isEmpty(appIn.getName())){
            return renderResult(Global.FALSE, "操作失败,服务中文名称不能为空！");
        }else if(appIn.getAppSort() == null) {
            return renderResult(Global.FALSE, "操作失败,服务序号不能为空！");
        }else if(appIn.getAppStart() == null || StringUtils.isEmpty(appIn.getAppStart())) {
            return renderResult(Global.FALSE, "操作失败,启动命令不能为空！");
        }
        String message = appService.createServ(appIn);
        return renderResult(Global.TRUE, text("保存数据成功！"));

    }

    /**
     * 监控服务列表--跳转页面功能
     */
//	@RequiresPermissions("qianying:app")
    @RequestMapping(value = {"list", ""})
    public String list(AppIn appIn, Model model) {
//        List<AppOut> apps = appService.list();
//        model.addAttribute("appIn", appIn);


        SystemInfo systemInfo = appService.getSystemInfo();

         model.addAttribute("systemInfo", systemInfo);


        return "modules/monitor/serviceList";
    }

    /**
     * 查询单个服务，
     * @param appIn
     * @param model
     * @return
     */
    @RequestMapping(value = "queryServ")
    public String queryServ(AppIn appIn, Model model) {
        AppIn appIn2 = appService.queryBykid(appIn.getKid());
        model.addAttribute("appIn", appIn2);
        return "modules/monitor/monitorDataForm";
    }

    /**
     * 根据Kid删除服务
     * @param appIn
     * @return
     */
    @RequestMapping(value = "deleteServ")
    @ResponseBody
    public String deleteServ(AppIn appIn) {
        String message = "";
        if(StringUtils.isEmpty(appIn.getKid())) {
            return renderResult(Global.TRUE, "操作失败！");
        }
        message = appService.deleteServ(appIn);
        return renderResult(Global.TRUE, message);
    }

    @RequiresPermissions("line:view")
    @RequestMapping(value = "getSystemMonitor")
    @ResponseBody
    public SystemInfo getSystemMonitor() {
        SystemInfo systemInfo = appService.getSystemInfo();
        return systemInfo;
    }

    @RequiresPermissions("line:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<AppOut> listData(AppIn appIn, Model model, HttpServletRequest request, HttpServletResponse response) {
        System.err.println(JSONObject.toJSONString(appIn));
        List<AppOut> appOuts = null;
        if(null == appIn.getName()) {
            appIn.setName("");
        }
        if(null == appIn.getAppName()) {
            appIn.setAppName("");
        }
        if((!appIn.getName().isEmpty()) || (!appIn.getAppName().isEmpty())) {
            appOuts = appService.queryListServ(appIn);
        }else {
            appOuts = appService.queryAll(appIn);
        }
        List<AppOut> outApps = page(request, appIn, appOuts);
        Page<AppOut> page = new Page();
        page.setList(outApps);
        page.setCount(appOuts.size());
        page.setPageNo(appIn.getPageNumber());
        page.setPageSize(20);


        SystemInfo systemInfo = appService.getSystemInfo();

        model.addAttribute("systemInfo", systemInfo);



        return page;
    }

    /*
    分页
     */
    private List<AppOut> page(HttpServletRequest request, AppIn appIn, List<AppOut> list){
        Integer size = 20;
        Integer num = appIn.getPageNumber();
        List<AppOut> outApps = new ArrayList<AppOut>();
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        if(!StringUtils.isEmpty(pageSize)){
            size = Integer.parseInt(pageSize);
            appIn.setPageSize(size);
        }
        if(!StringUtils.isEmpty(pageNo)){
            num = Integer.parseInt(pageNo);
            appIn.setPageNumber(num);
        }
        int mo = list.size() % size;
        if(mo == 0) {
            outApps = list.subList((num-1) * size, num * size);
            log.info(JSONObject.toJSONString(outApps));
        }else {
            if(list.size()/(num*size) == 0) {
                outApps = list.subList((num-1)*size, list.size());
            }else {
                outApps = list.subList((num-1) * size, num * size);
            }
        }
        return outApps;
    }

    /**
     * 执行命令
     * <p>
     * 启动服务1
     * 停止服务2
     * 重启服务3
     */
//    @RequiresPermissions("qianying:app")
    @RequestMapping(value = "exec")
    @ResponseBody
    public String exec(AppIn appIn) {
        String message = "";
        Integer commandType = appIn.getCommandType();
        if(appIn.getAppName().isEmpty()) {
            return renderResult(Global.TRUE, "操作失败！");
        }
        if(commandType==1 || commandType==2 || commandType==3) {
            message = appService.exec(appIn);
        }else {
            return renderResult(Global.TRUE, "操作失败！");
        }
        return renderResult(Global.TRUE, message);
    }

    @GetMapping(value = "execAll")
    @ResponseBody
    public String oneKeyExec(@RequestParam Integer command) {
        String message = "";
        if(command ==1 || command == 2) {
            message = appService.oneKeyExec(command);
        }else {
            return renderResult(Global.TRUE, "操作失败！");
        }
        return renderResult(Global.TRUE, message);
    }

}