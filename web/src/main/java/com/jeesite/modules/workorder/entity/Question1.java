package com.jeesite.modules.workorder.entity;

import lombok.Data;


@Data
public class Question1{

    private String kid;

    private String parentKid;

    private String questionName;

    private Integer questionOrder;

    private Boolean isLastLevel;
}
