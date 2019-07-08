package com.jeesite.modules.line.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Line{

    private String lineKid;

    private String lineTitle;

    private String realName;

    private String picUrl;

    private Double tripPrice ; //路线价格

    private String departureDate;

    private Integer status;

    private Integer inCompleteOrder;

//    private String phone = "";
//
//    private String pulishTime = "";
//
//    private String auditTime = "";
//
//    private String remarks = "";

}
