package com.jeesite.modules.workorder.entity;

import lombok.Data;

import java.util.List;

@Data
public class FormDetailOutVo {

    private String detailDesc = "";

    private String username = "";

    private String userImage = "";

    private String createTime = "";

    private List<FormFileOutVo> formFileOutVos;

}
