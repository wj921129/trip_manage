package com.jeesite.modules.line.entity;

import lombok.Data;

@Data
public class NewLineCostOutVo {

    private String kid;

    private String lineKid;

    private String costInclude; //费用包含

    private String tripRule; //行程规则

    private String refundInstruction; //改退说明

    private Double tripPrice; //路线价格

    private Integer travelDays; // 旅行天数
}
