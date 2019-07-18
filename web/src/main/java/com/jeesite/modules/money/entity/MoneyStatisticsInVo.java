package com.jeesite.modules.money.entity;

import lombok.Data;

@Data
public class MoneyStatisticsInVo {

    private Integer countValue;

    private String fromDay;

    private String toDay;

    private Integer pageNo;

    private Integer pageSize;
}
