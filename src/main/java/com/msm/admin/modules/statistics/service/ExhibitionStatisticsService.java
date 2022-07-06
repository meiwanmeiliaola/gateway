package com.msm.admin.modules.statistics.service;

import com.msm.admin.modules.statistics.entity.ChartData;
import com.msm.admin.modules.statistics.mapper.ExhibitionStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author quavario@gmail.com
 * @date 2020/1/2 15:47
 */
@Service
public class ExhibitionStatisticsService {
    @Autowired
    ExhibitionStatisticsMapper exhibitionStatisticsMapper;

    /** 按部门统计 */
    public List<ChartData> getByDepart() {
        List<ChartData> chartData = exhibitionStatisticsMapper.selectByDepart();
        return chartData.stream().peek(item -> item.setTitle(Optional.ofNullable(item.getTitle()).orElse("其他"))).collect(Collectors.toList());
    }
    

    /** 按类型统计 */
    public List<ChartData> getByType() {
        List<ChartData> chartData = exhibitionStatisticsMapper.selectByType();
        chartData.forEach(item -> {
            String title = null;
            switch (item.getTitle()) {
                case "1":
                    title = "文博新闻";
                    break;
                case "2":
                    title = "重大活动";
                    break;
            }
            item.setTitle(title);
        });
        return chartData;
    }

    

    /** 最近新增 */
    public Integer getRecentlyAdded() {
        return exhibitionStatisticsMapper.selectRecentlyAdded();

    }


    /** 最多展览 */
    public String getTopCount() {
        return exhibitionStatisticsMapper.selectTopCount();
    }

    public Integer getTotalByType(Integer type) {
        return exhibitionStatisticsMapper.selectTotalByType(type);
    }

    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("permanent", getTotalByType(1));
        map.put("temporary", getTotalByType(2));
        map.put("recentlyAdded", getRecentlyAdded());
        map.put("topCount", getTopCount());

        map.put("departChart", getByDepart());
        map.put("typeChart", getByType());
        return map;
    }
}
