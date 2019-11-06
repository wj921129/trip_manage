package com.jeesite.modules.monitor.entity.po;

import lombok.Data;

@Data
public class Cpu {
    //服务器当前时间
    private String currentTime;
    //运行时间
    private String runTime;
    //1分钟前系统负载的平均值
    private String loadAverage1;
    //5分钟前系统负载的平均值
    private String loadAverage5;
    //15分钟前系统负载的平均值
    private String loadAverage15;
    //当前系统进程总数
    private String taskTotal;
    //当前运行中的进程数
    private String taskRunning;
    //当前处于等待状态中的进程数
    private String taskSleeping;
    //使用率
    private String useRate;
}
