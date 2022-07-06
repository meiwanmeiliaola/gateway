package com.msm.admin.modules.statistics.controller;

import com.msm.admin.modules.statistics.service.ExhibitionStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description: 文物
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/statistics/exhibition")
public class ExhibitionStatisticsController {
    @Autowired
    ExhibitionStatisticsService exhibitionService;

    @GetMapping("")
    public Object getAll() {
        return exhibitionService.getAll();
    }
}
