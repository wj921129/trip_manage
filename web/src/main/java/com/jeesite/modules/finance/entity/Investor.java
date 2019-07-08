package com.jeesite.modules.finance.entity;

import lombok.Data;

@Data  //投资人管理
public class Investor {
    private String kid;   //投资人标识
    private String investorName;    //投资人姓名
    private String investorPhone;   //投资人电话
    private Double investorMoney;   //投资金额
    private String investorRemarks;  //投资人备注
    private String investorIntention;  //投资人意向
    //private Investor delFlag;
}
