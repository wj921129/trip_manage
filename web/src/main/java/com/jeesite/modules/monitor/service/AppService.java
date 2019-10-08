package com.jeesite.modules.monitor.service;

import com.jeesite.common.service.CrudService;
import com.jeesite.modules.monitor.entity.App;
import com.jeesite.modules.monitor.entity.AppDto;
import com.jeesite.modules.test.dao.TestDataDao;
import com.jeesite.modules.test.entity.TestData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
public class AppService extends CrudService<TestDataDao, TestData> {

    /**
     * 创建监控服务
     */
    public Integer create(AppDto appDto) {

        return 1;
    }

    /**
     * 监控服务列表
     */
    public List<App> list() {

        List<App> apps = null;

        return apps;
    }


    //启动服务1
    //停止服务2
    //重启服务3
    //刷新服务4

    // "执行命令"
    public Integer exec(AppDto appDto) {

        return 1;
    }

}