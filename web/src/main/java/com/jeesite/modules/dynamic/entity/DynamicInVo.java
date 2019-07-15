package com.jeesite.modules.dynamic.entity;

import com.jeesite.modules.commom.entity.BaseEntity;
import lombok.Data;

@Data
public class DynamicInVo extends BaseEntity {

    private String userKid;

    private String dynamicKid;

    private String discoverContent; //内容

    private Integer delFlag;

    private Integer insertType; //插入类型（0 不插入  1 图片  2 语音  3视频）

    private String meidaId; //资媒id

    private String mediaName; //资媒名称

    private String mediaTime; //资媒时长

    private String videoPic; //视频封面

    private String discoverLng; //经度

    private String discoverLat; //纬度

    private String discoverAddress; //位置

    private String publishTime; //发布时间

    private String discoverRemarks;



}
