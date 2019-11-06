package com.jeesite.modules.monitor.entity.vo;

import com.jeesite.modules.monitor.entity.po.Cpu;
import com.jeesite.modules.monitor.entity.po.Disk;
import com.jeesite.modules.monitor.entity.po.Memory;
import lombok.Data;

import java.util.List;

@Data
public class SystemInfo {
    private Cpu cpu;
    private Memory memory;
    private Disk disk;
}
