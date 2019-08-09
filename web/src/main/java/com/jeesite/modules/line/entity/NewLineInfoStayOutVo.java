package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.List;

@Data
public class NewLineInfoStayOutVo {

    private String kid;

    private String infoKid; //line_info_kid

    private String stayAddress; //住宿地址

    private Integer stayType; //住宿类型（10 酒店  20 名宿  30 青旅）

    private String stayDesc; //住宿描述

    private Integer stayIsSelf; // 住宿是否自理 10 自理 20不自理

    private List<LinePicOutVo> stayPics; //住宿图片存放地址

}
