/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.monitor.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.modules.monitor.entity.App;
import com.jeesite.modules.monitor.entity.AppDto;
import com.jeesite.modules.monitor.service.AppService;
import com.jeesite.modules.think.entity.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/app")
public class AppController extends BaseController {

    @Autowired
    private AppService appService;


    /**
     * 创建监控服务
     */
//	@RequiresPermissions("qianying:app")
    @RequestMapping(value = "create")
    public String create(@RequestBody AppDto appDto, Model model) {

        AppDto app = new AppDto();

        try {
            BeanUtils.copyProperties(app, appDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        appService.create(app);

        model.addAttribute("app", appDto);
        return "modules/user/userStatistics";

    }

    /**
     * 监控服务列表
     */
//	@RequiresPermissions("qianying:app")
    @RequestMapping(value = {"list", ""})
    public String list(AppDto appDto, Model model) {

        List<App> apps = appService.list();

        model.addAttribute("apps", apps);
        return "modules/user/userStatistics";
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
    public Integer exec(@RequestBody AppDto appDto) {

        Integer result = appService.exec(appDto);

        return result;
    }
}