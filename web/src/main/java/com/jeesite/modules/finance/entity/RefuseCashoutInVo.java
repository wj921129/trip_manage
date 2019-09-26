package com.jeesite.modules.finance.entity;

import lombok.Data;

@Data
public class RefuseCashoutInVo {
    private String cashoutKid; //提现kid
    private String refuseReason; //拒绝提现的原因

}
