package com.yuji.framework.web.domain.server;

import com.yuji.common.utils.Arith;
import lombok.Getter;
import lombok.Setter;

/**
 * CPU相关信息
 * 
 * @author Liguoqiang
 */
@Getter
@Setter
public class Cpu
{
    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;
}
