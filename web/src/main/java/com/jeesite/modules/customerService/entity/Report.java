package com.jeesite.modules.customerService.entity;

import lombok.Data;

@Data
public class Report {

    private String kid;//举报标识

    private String userKid;//举报用户标识

    private String nickname;//举报用户

    private String discoverKid;//发现唯一标识

    private Integer reportReason;//举报原因（10 诈骗信息  20 违法行为  30 涉黄信息  40 侵权行为  50 人身攻击  60 垃圾营销  70 其他）

    private String reportExplain;//举报补充说明（举报原因为其他时必填）

    private String reportTime;//举报时间

}
