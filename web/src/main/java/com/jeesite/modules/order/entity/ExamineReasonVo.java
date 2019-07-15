package com.jeesite.modules.order.entity;

import lombok.Data;

@Data  //后台管理审核不通过
public class ExamineReasonVo {
    private String refundKid; //退款单kid
    private String remarks;   //审核不通过原因
}
