package com.jeesite.modules.auth.entity;

import lombok.Data;

@Data
public class IdAuth {

    private String kid;

    private String nickname;

    private String idcardFrontImage;

    private String idcardBackImage;

    private Integer idAuth;
}
