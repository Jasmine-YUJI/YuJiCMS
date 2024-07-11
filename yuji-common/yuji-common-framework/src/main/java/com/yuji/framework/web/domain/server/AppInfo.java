package com.yuji.framework.web.domain.server;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppInfo {

    /**
     * 系统名称
     */
    private String name;

    /**
     * 系统代号
     */
    private String alias;

    /**
     * 系统版本
     */
    private String version;
}
