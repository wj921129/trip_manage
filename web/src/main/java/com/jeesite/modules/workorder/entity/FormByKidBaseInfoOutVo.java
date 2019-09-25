package com.jeesite.modules.workorder.entity;

import lombok.Data;

import java.util.List;

@Data
public class FormByKidBaseInfoOutVo {

    private String kid = "";

    private String userKid = "";

    private String username = "";

    private String phone = "";

    private String email = "";

    private Integer status;

}
