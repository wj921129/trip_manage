package com.jeesite.modules.monitor.entity;

import lombok.Data;

@Data
public class AppOut {
    //主键ID
    private Long id;
    //唯一ID
    private String kid;
    //服务名称
    private String appName;
    //服务中文名称
    private String name;
    //服务状态
    private Integer appStatus;
    //服务排序规则--从小到大
    private Integer appSort;
    //启动命令
    private String appStart;
    //删除标志
    private Integer delFlag;

    private String createBy = "";

    private String createTime = "";

    private String updateBy = "";

    private String updateTime = "";
}
