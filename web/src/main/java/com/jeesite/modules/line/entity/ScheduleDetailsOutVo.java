package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleDetailsOutVo {

    private Integer lineDay;

    private List<NewSchedulingOutVo> schedules;

    private List<NewLineInfoStayOutVo> stay; // 住宿

    private List<LineInfoTrafficOutVo> traffic; // 交通

}
