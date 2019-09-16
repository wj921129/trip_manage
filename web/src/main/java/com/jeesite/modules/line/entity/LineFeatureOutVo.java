package com.jeesite.modules.line.entity;

import lombok.Data;

import java.util.List;

@Data
public class LineFeatureOutVo {

    private String lineFeature;

    private List<LinePicOutVo> lineFeaturePics; //路线特色图片存放数组
}
