package com.jeesite.modules.advert.entity;

import lombok.Data;

/**
 * 广告实体
 */
@Data
public class Adt {

    //主键ID
    private Long id;

    //唯一ID
    private String kid;

    //删除标志
    private Integer delFlag;

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
    private String adtType;

    /**
     *  广告发布时间
     */
    private String adtPublishTime;

    /**
     *  广告开始时间
     */
    private String adtStartTime;

    /**
     *  广告结束时间
     */
    private String adtEndTime;

    /**
     *  广告展示状态 10展示 20不展示
     */
    private Integer adtStatus;
}
