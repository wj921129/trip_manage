package com.jeesite.modules.monitor.entity;

import lombok.Data;

@Data
public class App {
    //服务名称
    private String appName;
    //服务状态
    private Integer appStatus;
    //启动命令
    private String appStart;
}
