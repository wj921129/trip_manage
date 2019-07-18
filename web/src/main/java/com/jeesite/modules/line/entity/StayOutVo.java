package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StayOutVo {

    private String kid = "";

//    private String infoKid; //line_info_kid

    private String stayAddress = ""; //住宿地址

    private Integer stayType = 10; //住宿类型（10 酒店  20 名宿  30 青旅）

    private String stayDesc = ""; //住宿描述

    private Double stayPrice = 0.0; //住宿价格

    private List stayPics = new ArrayList<>();

}
