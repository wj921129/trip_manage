package com.jeesite.modules.monitor.entity;

import lombok.Data;

@Data
public class AppDto {
    //服务名称
    private String appName;
    //服务状态
    private Integer appStatus;
    //启动命令
    private String appStart;
    //命令类型
    private Integer commandType; //启动1 停止2 重新3 刷新4
}
