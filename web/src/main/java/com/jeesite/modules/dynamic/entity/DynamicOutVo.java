package com.jeesite.modules.dynamic.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DynamicOutVo {

    private String kid; //动态唯一标识

    private String userKid; //用户标识

    private String discoverContent; //内容

    private Integer insertType; //插入类型（0 不插入  1 图片  2 语音  3视频）

    private String mediaId; //资媒id

    private String mediaName; //资媒名称

    private String mediaTime; //资媒时长

    private String videoPic; //视频封面

    private String discoverLng; //经度

    private String discoverLat; //纬度

    private String discoverAddress; //位置

    private String discoverRemarks;

    private String publishTime; //发布时间

    private List picList = new ArrayList();

    private Integer delFlag; // 10正常 20已失效

    private String userImage; // 用户头像

    private String nickName; // 用户昵称

    private Integer bail; // 保证金

    private Float starLevel; // 用户星级

    private Integer collectNum; // 收藏数

    private Integer commentNum; //评论数

    private Integer likeNum; // 点赞数
}
