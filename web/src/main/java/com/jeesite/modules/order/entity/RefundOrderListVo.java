package com.jeesite.modules.order.entity;

import lombok.Data;

@Data  //退款单列表对象
public class RefundOrderListVo {
    private String refundKid;  //退款单kid
    private String refundReason;  //退款原因
    private String applyTime;  //申请退款时间
    private String applyName ; //申请人
    private String phone; //联系方式
    private Double refundMoney; //退款金额
    private Integer refundStatu; //退款单状态

}
