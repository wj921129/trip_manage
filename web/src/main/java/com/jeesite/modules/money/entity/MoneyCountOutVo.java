package com.jeesite.modules.money.entity;

import com.jeesite.modules.commom.entity.PageList;
import lombok.Data;

import java.util.List;

@Data
public class MoneyCountOutVo {

    private Double totalInMoney;

    private Double inMoney;

    private Double outMoney;

    private Double diffValue;

    private PageList<MoneyDetailsOutVo> moneyDetailsOutVo;

}
