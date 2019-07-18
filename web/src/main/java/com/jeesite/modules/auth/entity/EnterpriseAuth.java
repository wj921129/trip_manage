package com.jeesite.modules.auth.entity;

import lombok.Data;

@Data
public class EnterpriseAuth {

    private String kid;

    private String userKid;

    private String nickname;

    private String enterpriseName;

    private String dutyParagraph;

    private String enterpriseIntroduction;

    private String licenseImage;

    private Integer enterpriseAuth;

}
