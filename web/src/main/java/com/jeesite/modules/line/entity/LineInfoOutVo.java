package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LineInfoOutVo {

    private String kid = ""; //唯一标识

    private String userKid = ""; //用户标识

    private String lineTitle = ""; //路线标题

    private String departureDate = ""; //出发日期

    private String returnDate = ""; //返行日期

    private Integer travelDays = 0; //旅行天数

    private Integer lineStatu = 0; //路线状态（10 审核中  20 进行中  30已失效 40待审核）

    private String publishTime = ""; //发布时间

    private List homePics = new ArrayList();

    private Integer predictableNumber; // 可预订人数

    private Double tripPrice = 0.0; //路线价格


}
