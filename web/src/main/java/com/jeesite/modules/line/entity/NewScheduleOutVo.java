package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.List;

@Data
public class NewScheduleOutVo {

    private String kid;

    private String schedulingAddress; //行程地点

    private Integer schedulingType; //行程类型（10 上午  20 下午  30 晚上）

    private String activityName; //活动名称

    private String activityDesc; //活动描述

    private Integer guideCost; //是否包含导游费用（10  包含  20  不包含）

    private List<LinePicOutVo> activityPics; //行程图片存放地址

}
