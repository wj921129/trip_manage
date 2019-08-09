package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.List;

@Data
public class NewLineOutVo {

    private String kid; // lineKid

    private String userKid; // 用户kid

    private Integer predictableNumber; // 可预订人数

    private String lineFeature; // 路线特色

    private Integer travelDays; // 旅行天数

    private String lineTitle; // 路线标题

    private String lineLng; // 经度

    private String lineLat; // 纬度

    private String lineAddress; // 路线游玩地址

    private List<LinePicOutVo> homePics; //主页图片存放数组

    private Integer lineStatu; //路线状态（10 审核中  20 进行中  30已失效 40待审核）

    private String publishTime; //发布时间

    private Double tripPrice; //路线价格

    private String costInclude; // 费用包含

    private String tripRule; // 行程规则

    private String refundInstruction; // 改退说明

    private Integer delFlag;


}
