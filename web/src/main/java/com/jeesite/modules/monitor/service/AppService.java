package com.jeesite.modules.monitor.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.commom.utils.ApiPage;
import com.jeesite.modules.commom.utils.ApiResult;
import com.jeesite.modules.commom.utils.ApiUtils;
import com.jeesite.modules.line.entity.NewLineDetailsOutVo;
import com.jeesite.modules.monitor.entity.AppOut;
import com.jeesite.modules.monitor.entity.AppIn;
import com.jeesite.modules.test.dao.TestDataDao;
import com.jeesite.modules.test.entity.TestData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.plugin2.message.Message;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly=true)
public class AppService extends CrudService<TestDataDao, TestData> {
    @Value("${api.host}")
    private String apiHost;

    private static final String list = "/monitor/app/list";

    private static final String queryServ = "/monitor/app/queryServ";

    private static final String condition = "/monitor/app/condition";

    private static final String createServ = "/monitor/app/createServ";

    private static final String deleteServ = "/monitor/app/deleteServ";

    private static final String exec = "/monitor/app/exec";

    private static final String execAll = "/monitor/app/execAll";

    /**
     * 创建或编辑监控服务
     */
    public String createServ(AppIn appIn) {
        String message = "";
        String s = JSONObject.toJSONString(appIn);
        JSONObject appInJson = JSONObject.parseObject(s);
        String result = ApiUtils.post(apiHost + createServ, appInJson);
        if (result != null){
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject != null){
                String code = jsonObject.getString("code");
                if ("200".equals(code) && "success".equals(jsonObject.getString("msg"))){
                    message = "操作成功!";
                    log.info("服务 : " + appIn.getAppName() + "操作成功!");
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

    /**
     * 查询服务列表
     * @return List
     */
    public List<AppOut> queryAll(AppIn appIn) {
//        ApiResult<ApiPage<AppOut>> page = ApiUtils.getPage(apiHost + list, appIn, AppOut.class);
        ApiResult<List<AppOut>> listApiResult = ApiUtils.get(apiHost + list, appIn, AppOut.class);
//        ApiPage<AppOut> data = page.getData();
        List<AppOut> data = listApiResult.getData();
        return data;
    }

    /**
     * 条件查询
     * @param appIn
     * @return
     */
    public List<AppOut> queryListServ(AppIn appIn) {
        ApiResult<List<AppOut>> listApiResult = ApiUtils.get(apiHost + condition, appIn, AppOut.class);
        List<AppOut> data = listApiResult.getData();
        return data;
    }

    /**
     * 查询单个服务
     * @param kid
     * @return
     */
    public AppIn queryBykid(String kid) {
        AppIn appIn = new AppIn();
        String result = ApiUtils.get(apiHost + queryServ + "?kid=" + kid);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if(jsonObject != null) {
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            if("200".equals(code) && "success".equals(msg)) {
                appIn = jsonObject.getObject("data", AppIn.class);
            }else {
                if(jsonObject.getString("errorMsg") != null) {
                    log.info(jsonObject.getString("errorMsg"));
                }
            }
        }
        return appIn;
    }

    /**
     * 删除服务
     * @param appIn
     */
    public String deleteServ(AppIn appIn) {
        String message = "";
//        AppIn appIn2 = queryServ(appIn.getKid());
//        appIn2.setDelFlag(20);

        String result = ApiUtils.get(apiHost + deleteServ + "?kid=" + appIn.getKid());
        JSONObject jsonObject = JSONObject.parseObject(result);
        if(jsonObject != null) {
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            if("200".equals(code) && "success".equals(msg)) {
                message = "删除服务成功!";
                log.info("删除 : " + appIn.getAppName() + "操作成功!");
            }else {
                if (jsonObject.getString("errorMsg") != null){
                    message = jsonObject.getString("errorMsg");
                }
                log.info(jsonObject.getString("errorMsg"));
            }
        }
        return message;
    }

    //启动服务1
    //停止服务2
    //重启服务3
    //刷新服务4

    // "执行命令"
    public String exec(AppIn appIn) {
        log.info("操作服务，入参 :" + appIn);
        String message = "";
        String result = ApiUtils.post(apiHost + exec, appIn);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if(jsonObject != null) {
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            if("200".equals(code) && "success".equals(msg)) {
                message = "操作成功";
//                message = appIn.getAppName();
                log.info("服务 :" + appIn.getAppName() + "操作成功");
            }else {
                if(jsonObject.getString("errorMsg") != null) {
                    message = jsonObject.getString("errorMsg");
                }
                log.info(jsonObject.getString("errorMsg"));
            }
        }
        return message;
    }


    public String oneKeyExec(Integer command) {
        log.info("操作服务，入参：" + command);
        String message = "";
        String result = ApiUtils.post(apiHost + execAll, command);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if(jsonObject != null) {
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            if("200".equals(code) && "success".equals(msg)) {
//                message = appIn.getAppName();
                if(command == 1) {
                    message = "开启成功";
                    log.info("开启成功");
                }else if(command == 2) {
                    message = "关闭成功";
                    log.info("关闭成功");
                }
            }else {
                if(jsonObject.getString("errorMsg") != null) {
                    message = jsonObject.getString("errorMsg");
                }
                log.info(jsonObject.getString("errorMsg"));
            }
        }
        return message;
    }
}