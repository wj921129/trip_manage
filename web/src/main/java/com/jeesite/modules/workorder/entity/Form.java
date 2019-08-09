package com.jeesite.modules.workorder.entity;

import lombok.Data;

import java.util.List;

@Data
public class Form {

    private String kid = "";

    private String userKid = "";

    private String nickname = "";

    private String categoryKid = "";

    private String categoryName = "";

    private String questionDesc = "";

    private List<FormFile> imageOutVos;

    private List<FormFile> fileOutVos;

    private String realname = "";

    private String phone = "";

    private String email = "";

    private Integer status;

    private Integer solutions;

    private String resolvent;
}
