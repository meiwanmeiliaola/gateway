package com.msm.admin.modules.statistics.controller;

import com.msm.admin.modules.statistics.entity.ChartData;
import com.msm.admin.modules.statistics.service.PostStatisticsService;
import com.msm.admin.modules.statistics.service.RelicStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @Description: 文物
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/statistics/post")
public class PostStatisticsController {
    @Autowired
    PostStatisticsService postStatisticsService;

    @GetMapping()
    public Object getAll() {
        return postStatisticsService.getAll();
    }
}
