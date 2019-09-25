package com.jeesite.modules.workorder.entity;

import lombok.Data;

import java.util.List;

@Data
public class FormReplyAddInVo {

    private String workOrderKid;

    private Integer replyType;

    private String replyDesc;

    private List<String> imageUrls;

    private String replyUsername;

}
