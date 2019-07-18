package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LineTouristInfoOutVo {

    private String userImage = ""; // 导游头像

    private String nickName = ""; // 导游昵称

    private Float starLevel = 0f; // 导游星级

    private Integer bail = 10; // 导游保证金(10未缴 20已缴)

    private Integer idAuth = 10; //身份验证（10 暂未验证  20 验证通过  30验证未通过）

    private String sex = "男"; //导游性别

    private String education = ""; //导游学历

    private Integer age = 0; //导游年龄

    private List<UserLabelOutvo> labelOutvoList = new ArrayList<>(); // 导游标签

    private List<UserLanguageOutVo> languages = new ArrayList<>(); // 导游语言
}
