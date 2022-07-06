package com.msm.admin.modules.monitor.controller;

import com.msm.admin.modules.monitor.entity.Server;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/server")
public class SysServerController {

    @GetMapping("/info")
    public Server server() throws Exception {
        Server server = new Server();
        server.copyTo();
        return server;
    }
}
