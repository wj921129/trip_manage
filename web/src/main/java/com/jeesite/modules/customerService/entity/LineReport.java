package com.jeesite.modules.customerService.entity;

import lombok.Data;

@Data
public class LineReport {

    private String lineKid;

    private String lineTitle;

    private Integer reportCount;

    private Integer reportStatus;
}
