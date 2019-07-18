package com.jeesite.modules.line.entity;

import com.jeesite.modules.commom.entity.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LineSearchOutVo extends BaseEntity {

    private String lineKid;

    private String userKid;

    private String lineTitle = "";

    private String realName;

    private List linePics = new ArrayList();

    private Double tripPrice = 0.0; //路线价格

    private Integer status = 10;

    private Integer inCompleteOrder = 0;

    private String phone = "";

    private String pulishTime = "";

    private String auditTime = "";

    private String remarks = "";

    private Integer lineDisplay;

    private Integer lineDay;

    private String deparementDate;

}
