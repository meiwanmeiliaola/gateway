package com.msm.admin.modules.analysis.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.analysis.entity.VisitView;
import com.msm.admin.modules.analysis.service.StatViewService;
import com.msm.admin.modules.analysis.service.VisitViewService;
import com.msm.admin.modules.statistics.entity.ChartData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description: 统计分析api
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/anal")
public class ViewController {

    @Autowired
    private StatViewService viewService;

    @Autowired
    private VisitViewService visitViewService;


    /**
     * 根据类型查询热门文物
     * @param type 文物类型 {@link com.msm.admin.modules.relic.entity.Relic}
     * @return ChartData
     */
    @RequestMapping("/relic/pop")
    List<ChartData> getPopRelic(Integer type) {
       return viewService.getPopRelic(type);
    }

    /**
     * 热门文物类别
     * @return 热门文物类别
     */
    @RequestMapping("/relic/popRelicCatg")
    List<ChartData> getPopRelicCatg() {
        return viewService.selectPopRelicCatg();
    }


    /**
     * 查询近nearDay page view总数
     * @param nearDay 天
     * @return page view
     */
    @RequestMapping("/visit/pv/{nearDay}")
    Object getPageViewCountByNearDay(@PathVariable Integer nearDay) {
        return visitViewService.getPageViewCountByNearDay(nearDay);
    }

    /**
     * @param nearDay 天数
     * @return 近nearDay访客数
     */
    @RequestMapping("/visit/uv/{nearDay}")
    Object getUserViewCountByNearDay(@PathVariable Integer nearDay) {
        return visitViewService.getUserViewCountByNearDay(nearDay);
    }

    @RequestMapping("/bannerInfo")
    Object getBannerInfo() {
        Map<String, Object> map = new HashMap<>(16);
        Object totalPageView = visitViewService.getPageViewCountByNearDay(0);
        Object dayPageView = visitViewService.getPageViewCountByNearDay(1);

        Object dayUserView = visitViewService.getUserViewCountByNearDay(1);
        Object totalUserView = visitViewService.getUserViewCountByNearDay(0);

        map.put("totalPv", totalPageView);
        map.put("totalUv", totalUserView);
        map.put("dayPv", dayPageView);
        map.put("dayUv", dayUserView);
        return map;
    }

    @RequestMapping("/device")
    List<ChartData> getByDevice() {
        return visitViewService.selectByDevice();
    }

    @RequestMapping("/visitView/list")
    Object list(Page<VisitView> page) {
        return visitViewService.page(page, Wrappers.<VisitView>lambdaQuery().orderByDesc(VisitView::getRecordDate));
    }
}
