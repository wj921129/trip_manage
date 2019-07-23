package com.jeesite.modules.dynamic.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DynamicByKidOutVo {

    private String ifExist = "false";

    private String kid = ""; //动态唯一标识

    private String userKid = ""; //用户标识

    private String discoverContent = ""; //内容

    private Integer insertType = 0; //插入类型（0 不插入  1 图片  2 语音  3视频）

    private String mediaId = ""; //资媒id

    private String mediaName = ""; //资媒名称

    private String mediaTime = ""; //资媒时长

    private String videoPic = ""; //视频封面

    private List picList = new ArrayList();

    private Integer delFlag; // 10正常 20已失效
}
