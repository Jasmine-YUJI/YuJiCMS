package com.yuji.web.controller.monitor;

import com.yuji.common.config.YuJiConfig;
import com.yuji.common.utils.DateUtils;
import com.yuji.framework.web.domain.server.AppInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yuji.common.core.domain.AjaxResult;
import com.yuji.framework.web.domain.Server;

/**
 * 服务器监控
 * 
 * @author Liguoqiang
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController
{

    @Autowired
    private YuJiConfig yuJiConfig;

    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping()
    public AjaxResult getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }

    @GetMapping("/dashboard")
    public AjaxResult getDashboardInfo() {
        DashboardServerData server = new DashboardServerData();
        server.getApp().setName(yuJiConfig.getName());
        server.getApp().setAlias(yuJiConfig.getAlias());
        server.getApp().setVersion(yuJiConfig.getVersion());
        server.setStartTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getServerStartDate()));
        server.setRunTime(DateUtils.timeDistance(DateUtils.getNowDate(), DateUtils.getServerStartDate()));
        return AjaxResult.success(server);
    }

    @Getter
    @Setter
    static class DashboardServerData {

        private AppInfo app = new AppInfo();

        private String startTime;

        private String runTime;
    }
}
