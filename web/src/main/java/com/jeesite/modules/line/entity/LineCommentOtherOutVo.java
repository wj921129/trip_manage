package com.jeesite.modules.line.entity;

import lombok.Data;

@Data
public class LineCommentOtherOutVo {

    private String commentContent = ""; // 第一条评论内容

    private String commentTime = ""; // 第一条评论的时间

    private String commentUserNickname = ""; // 第一条用户的评论昵称

    private String commentUserImage = ""; // 第一条用户的评论头像

    private Float commentUserStarLevel = 0.0f; // 第一条评论的用户星级

    private Integer commentUserBail = 10; // 第一条评论用户的保证金 (10未缴 20已缴)

    private String commentUserKid = ""; // 第一条评论用户kid

    private Integer commentNum = 0; // 路线评价数量
}
