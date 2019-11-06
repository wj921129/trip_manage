package com.jeesite.modules.monitor.entity.po;

import lombok.Data;

@Data
public class Memory {
    //内存名字
    private String name;
    //内存总大小
    private String total;
    //已使用内存数
    private String used;
    //空闲内存数
    private String free;
    //使用率
    private String useRate;
}
