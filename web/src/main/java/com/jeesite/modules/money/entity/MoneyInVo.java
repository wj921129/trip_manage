package com.jeesite.modules.money.entity;

import lombok.Data;

@Data
public class MoneyInVo {

    private Integer countValue;

    private String fromDay;

    private String toDay;

    private Integer pageNumber = 1;

    private Integer pageSize = 10;
}
