package com.jeesite.modules.commom.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {

    private Integer pageSize = 10;

    private Integer pageNumber = 1;

}
