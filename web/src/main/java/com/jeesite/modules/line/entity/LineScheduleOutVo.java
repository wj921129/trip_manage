package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LineScheduleOutVo {

    private Integer lineDay = 0;

//    private MorningScheduleOutVo morningSchedule;

    private List<SchedulingOutVo> lineSchedules = new ArrayList<>();

    private List<DinnerOutVo> lineDinners = new ArrayList<>();

    private List<StayOutVo> lineStays = new ArrayList<>();

    private List<TrafficOutVo> lineTraffics = new ArrayList<>();

//    private LineCostOutVo lineCost;

}
