package com.jeesite.modules.finance.entity;

import lombok.Data;

@Data
public class RefundBail {

    private String kid;

    private String userKid;

    private String nickname;

    private Integer refundStatu;

    private Integer refundReason;

    private String refundExplain;

    private String applyTime;
}
