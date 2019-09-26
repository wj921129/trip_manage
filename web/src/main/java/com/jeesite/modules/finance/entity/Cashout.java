package com.jeesite.modules.finance.entity;

import lombok.Data;

@Data
public class Cashout {
    private String kid;
    private String userKid;
    private Integer cashoutType; //提现类型(10微信 20支付宝)
    private Integer cashoutStatus; //提现状态(10 待审核 20 审核通过 30审核不通过)
    private Double cashoutMoney;
    private String cashoutTime;
    private String cashoutRemarks;
}
