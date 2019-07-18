package com.jeesite.modules.line.entity;

import lombok.Data;

@Data
public class ScheduleDetailsOutVo {

    private Integer lineDay;

    private ScheduleAfternoonOutVo morning; // 上午

    private ScheduleAfternoonOutVo afternoon; // 下午

    private ScheduleAfternoonOutVo night; // 晚上

    private ScheduleStayOutVo stay; // 住宿

    private ScheduleTrafficOutVo traffic; // 交通

}
