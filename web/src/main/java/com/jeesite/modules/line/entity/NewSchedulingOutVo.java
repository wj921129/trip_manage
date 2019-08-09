package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.List;

@Data
public class NewSchedulingOutVo {

    private Integer schedulingType; // 10 上午 20 下午 30 晚上

    private List<LineInfoDinnerOutVo> dinners; // 用餐

    private List<NewScheduleOutVo> activitys; // 活动

}
