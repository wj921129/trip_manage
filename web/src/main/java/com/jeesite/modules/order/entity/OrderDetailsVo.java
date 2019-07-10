package com.jeesite.modules.order.entity;

import lombok.Data;

@Data   //订单详情对象
public class OrderDetailsVo {
    //订单信息
    private String orderKid;  //订单kid
    private Integer orderStatu; //订单状态
    //游客信息
    private String touristName;  //游客姓名
    private String touristSex;  // 游客性别
    private String touristPhone; //游客电话

    //导游信息
    private String guideName;  //导游姓名
    private String guideSex;  // 导游性别
    private String guidePhone; //导游电话

    //路线信息
    private String lineTitle;  //路线标题
    private String travelDate; //出行时间
    private Integer travelDays; //旅行天数
    private Integer touristNum; //游客数量

    //支付信息
    private Double payMoney; //支付金额
    private String payTime;  //支付时间
}
