package com.msm.admin.modules.pub.controller;

import com.msm.admin.modules.analysis.entity.VisitView;
import com.msm.admin.modules.analysis.service.VisitViewService;
import com.msm.admin.modules.common.service.impl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

/**
 * 前端访问统计api
 * @author quavario@gmail.com
 * @date 2020/2/4 17:24
 */
@RestController
@RequestMapping("/v1/view")
public class PubViewController {

    @Autowired
    VisitViewService visitViewService;

    @RequestMapping("/visit/{url}")
    public void addVisitView(@PathVariable String url) {
        url = new String(Base64.getUrlDecoder().decode(url));
        VisitView visitView = new VisitView().setUrl(url);
        visitViewService.save(visitView);
    }

    @RequestMapping("/add")
    public void add() {

    }

    @Autowired
    RedisService redisService;

    @RequestMapping("/get")
    public Object get() {
        Object total_access = redisService.get("total_access");
        return total_access;
    }
}
