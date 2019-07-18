package com.jeesite.modules.money.entity;

import lombok.Data;

@Data
public class MoneyDetailsOutVo {

    private String date;

    private Double inMoney;

    private Double outMoney;

    private Double theDaydiffValue;

}
