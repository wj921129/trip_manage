package com.jeesite.modules.monitor.entity;

import com.jeesite.modules.commom.entity.BaseEntity;
import lombok.Data;

@Data
public class AppIn extends BaseEntity {
    //唯一ID
    private String kid;
    //服务名称
    private String appName;
    //服务中文名称
    private String name;
    //服务状态
    private Integer appStatus;
    //服务排序
    private Integer appSort;
    //启动命令
    private String appStart;
    //命令类型
    private Integer commandType; //启动1 停止2 重新3 刷新4


}
