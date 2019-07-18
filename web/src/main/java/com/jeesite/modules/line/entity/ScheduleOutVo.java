package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScheduleOutVo {

    private LineInfoOutVo line;

    private Integer commentNum = 0; // 路线评价数量

    private String lineFeature = ""; // 路线特色

    private List<LineScheduleOutVo> scheduleList = new ArrayList<>(); // 行程安排

    private LineCostOutVo lineCost; // 费用须知

    private Integer isCollect = 20; // 是否收藏 10是 20否

    private LineCommentOutVo lineComment; // 路线第一条评价相关信息

    private LineTouristInfoOutVo touristInfo; // 导游相关信息

    private Integer delFlag;

}
