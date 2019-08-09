package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.List;

@Data
public class NewLineDetailsOutVo {

    private NewLineOutVo line;

    private LineFeatureOutVo lineFeature;

    private NewLineCostOutVo lineCost;

    private LineTouristInfoOutVo touristInfo;

    private LineCommentOutVo lineComment;

    private List<ScheduleDetailsOutVo> scheduleDetails;

    private Integer isCollect; // 是否收藏


}
