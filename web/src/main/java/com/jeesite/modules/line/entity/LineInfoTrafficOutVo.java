package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.List;

@Data
public class LineInfoTrafficOutVo {

    private String kid;

    private String infoKid; //line_info_kid

    private String trafficDesc; //交通描述

    private Integer trafficIsSelf; // 交通是否自理

    private List<LinePicOutVo> trafficPics; //交通图片存放地址

}
