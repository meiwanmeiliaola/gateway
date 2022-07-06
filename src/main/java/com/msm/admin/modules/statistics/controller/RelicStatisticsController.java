package com.msm.admin.modules.statistics.controller;

import com.msm.admin.modules.statistics.entity.ChartData;
import com.msm.admin.modules.statistics.service.RelicStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Description: 文物
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/statistics/relic")
public class RelicStatisticsController {
    @Autowired
    RelicStatisticsService relicStatisticsService;

    /**
     * @return 按城市分类
     */
    @RequestMapping("/area")
    List<ChartData> getByArea() {
        List<ChartData> chartData = relicStatisticsService.getByArea();
        return chartData;
    }

    /**
     * @return 按部门分类
     */
    @RequestMapping("/dep")
    List<ChartData> getByDepart() {
        List<ChartData> chartData = relicStatisticsService.getByDepart();
        return chartData;
    }




    @RequestMapping("/basicInfo")
    Object getBasicInfo() {
        return relicStatisticsService.getBasicInfo();
    }


    @RequestMapping("/info/pipe")
    Object getRelicInfoPipe() {
        return relicStatisticsService.getRelicInfoPipe();
    }

    @RequestMapping("/info/bar")
    Object getRelicInfoBar() {
        return relicStatisticsService.getRelicInfoBar();
    }

}
