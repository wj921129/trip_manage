package com.jeesite.modules.order.entity;

import lombok.Data;

@Data
public class AddOrderVo {
    private String userKid;   //用户Kid
    private String lineKid;   //路线Kid
    private String realname;  //联系人
    private String phone;   //联系方式
    private String orderRemarks;  //订单备注
    private Integer adultNumber;  //成人人数
    private Integer childrenNumber;  //儿童人数
    private Double totalMoney;  //支付总金额
    private String travelDate;  //出行日期


}
