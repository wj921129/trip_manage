package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.List;

@Data
public class LineInfoDinnerOutVo {

    private String kid;

    private String infoKid; //line_info_kid

    private Integer dinnerIsSelf; //用餐是否自理（10 自理  20 不自理）

    private Integer dinnerType; //用餐类型（10 早餐  20 午餐 30 晚餐）

    private String dinnerDesc; //用餐描述

    private List<LinePicOutVo> dinnerPics;
}
