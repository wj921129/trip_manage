package com.jeesite.modules.monitor.entity.po;

import lombok.Data;

@Data
public class Disk {
    //文件路径
    private String fileSystem;
    //硬盘大小
    private String size;
    //已用大小
    private String used;
    //可用大小
    private String avail;
    //使用率
    private String useRate;
    //挂载
    private String mounted;
}
