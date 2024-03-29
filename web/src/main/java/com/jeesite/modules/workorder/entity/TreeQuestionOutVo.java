package com.jeesite.modules.workorder.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeQuestionOutVo {

    private String kid = "";

    private String parentKid = "";

    private String parentQuestionName = "";

    private String questionName = "";

    private String questionOrder;

    private Boolean isLastLevel = true;

    private List<TreeQuestionOutVo> children = new ArrayList<>();

}
