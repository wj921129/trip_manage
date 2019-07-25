package com.jeesite.modules.finance.entity;

import lombok.Data;

@Data
public class Withdraw {

    private String kid = "";

    private String userKid = "";

    private String nickname = "";

    private Double withdrawMoney = 0.0;//提现金额

    private Integer withdrawStatu = 10;//提现状态（10 提现中  20 提现完成  30 异常）

    private String applyTime= "";//申请时间

    private String paymentTime= "";//到账时间

    private String withdrawBankCardKid= "";//提现银行卡kid

    private String withdrawBankCardNumber = "";//提现银行卡号

    private String openingBank = "";//开户行

    private String cardholderRealname = "";//持卡人姓名

    private String idcardNumber = "";//身份证号码

    private String phone = "";//手机号

    private String withdrawRemark= "";//提现备注
}
