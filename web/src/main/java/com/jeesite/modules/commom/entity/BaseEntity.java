package com.jeesite.modules.commom.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {

    private Integer pageNumber = 1;

    private Integer pageSize = 10;

}
