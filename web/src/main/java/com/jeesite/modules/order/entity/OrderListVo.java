package com.jeesite.modules.order.entity;

import lombok.Data;

@Data
public class OrderListVo{
    private  String orderKid;  //订单号
    private  Integer orderStatu;  //订单状态
    private  String createTime; //订单创建时间
    private  String payTime; //支付时间
    private  String realname;   //姓名
    private  String phone;     //手机
    private  Double payMoney;  //支付金额
    private  String lineTitle; //路线标题
}
