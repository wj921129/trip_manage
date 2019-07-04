package com.jeesite.modules.customerService.entity;

import lombok.Data;

@Data
public class Complain {

    private String kid;

    private String userKid;

    private String nickname;

    private String orderKid;

    private String lineTitle;

    private Integer complainStatu;

    private Integer complainReason;

    private String complainExplain;

    private String contactPhone;

    private String complainTime ;

}
