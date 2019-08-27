package com.jeesite.modules.configCenter.entity;

import lombok.Data;

@Data
public class UpdateConfigVo {
    private String configKid;  //配置kid
    private String configType;  //配置类型
    private String configName;  //配置名称
    private String configValue; // 配置值
    private String operationName; //操作人
    private Integer delFlag; //删除标识
}
