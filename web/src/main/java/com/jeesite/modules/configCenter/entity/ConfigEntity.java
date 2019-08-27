package com.jeesite.modules.configCenter.entity;

import lombok.Data;

@Data
public class ConfigEntity {
    private String configKid;   //配置标识
    private String configType;  //配置类型
    private String configName;  //配置名称
    private String configValue; // 配置值
    private String operationName; //操作人
    private String createTime; //创建时间
    private String updateTime; //修改时间
    private Integer delFlag;  //删除标识
}
