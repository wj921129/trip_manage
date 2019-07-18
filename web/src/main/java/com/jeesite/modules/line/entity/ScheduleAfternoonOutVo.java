package com.jeesite.modules.line.entity;

import lombok.Data;

@Data
public class ScheduleAfternoonOutVo {

    private ScheduleDinnerOutVo scheduleDinnerOutVo; // 用餐

    private ScheduleActivityOutVo scheduleActivityOutVo; // 活动

}
