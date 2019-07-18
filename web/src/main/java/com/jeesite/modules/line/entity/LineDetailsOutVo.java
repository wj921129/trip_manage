package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.List;

@Data
public class LineDetailsOutVo {

    private LineInfoOutVo lineInfo;

    private LineTouristInfoOutVo touristInfo;

    private LineCostOutVo lineCost;

    private LineFeatureOutVo lineFeature;

    private LineCommentOtherOutVo lineComment;

    private List<ScheduleDetailsOutVo> scheduleDetailsOutVos;

}
