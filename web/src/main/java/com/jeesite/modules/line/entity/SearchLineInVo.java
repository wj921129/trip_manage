package com.jeesite.modules.line.entity;

import com.jeesite.modules.commom.entity.BaseEntity;
import lombok.Data;

@Data
public class SearchLineInVo extends BaseEntity {

    private String lineKid;

    private String userKid;

    private String lineTitle; // 路线主题

    private Integer lineStatu; // 路线状态（10审核中  20进行中  30已失效 40待审核 50审核不通过）

    private String phone; // 手机号

    private String publishTime; // 发布时间

    private String userName; // 导游姓名

    private String departureDate; // 出发时间

    private Integer lineDisplay; // 展示

}
