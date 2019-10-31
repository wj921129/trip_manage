package com.jeesite.modules.advert.entity;

import com.jeesite.modules.commom.entity.BaseEntity;
import lombok.Data;

@Data
public class AdtInVo extends BaseEntity {

    /**
     *  广告kid
     */
    private String kid;

    /**
     *  广告资源kid
     */
    private String adtResourceKid;

    /**
     *  广告发布人
     */
    private String adtUserKid;

    /**
     *  广告内容
     */
    private String adtContent;

    /**
     *  广告图片地址
     */
    private String adtImageUrl;

    /**
     *  广告标题
     */
    private String adtTitle;

    /**
     *  广告资源类型
     */
    private Integer adtType;

    /**
     *  广告开始时间
     */
    private String adtStartTime;

    /**
     *  广告结束时间
     */
    private String adtEndTime;

    /**
     *  广告展示状态 10展示 20不展示 30删除标识
     */
    private Integer adtStatus;
}
