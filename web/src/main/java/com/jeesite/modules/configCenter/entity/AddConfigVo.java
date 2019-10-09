package com.jeesite.modules.configCenter.entity;

import lombok.Data;

@Data
public class AddConfigVo {
    private String configKey;  //配置key
    private String configName;  //配置名称
    private String configValue; // 配置值
    private String operationName; //操作人
    private String configRemarks; //配置描述
}
