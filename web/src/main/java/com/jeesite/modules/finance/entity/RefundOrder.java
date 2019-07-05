package com.jeesite.modules.finance.entity;

import lombok.Data;

@Data
public class RefundOrder {

    private String kid;

    private String orderKid;

    private Double refundMoney;

    private String refundAccount;

    private String refundReason;

    private String applyTime;
}
