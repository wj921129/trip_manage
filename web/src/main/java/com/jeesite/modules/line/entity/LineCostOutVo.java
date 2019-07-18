package com.jeesite.modules.line.entity;

import lombok.Data;

@Data
public class LineCostOutVo {

    private String costInclude = ""; //费用包含

    private String tripRule = ""; //行程规则

    private String refundInstruction = ""; //改退说明

}
