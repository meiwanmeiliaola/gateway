package com.msm.admin.modules.statistics.service;

import com.msm.admin.modules.statistics.entity.ChartData;
import com.msm.admin.modules.statistics.mapper.PostStatisticsMapper;
import com.msm.admin.modules.statistics.mapper.RelicStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author quavario@gmail.com
 * @date 2020/1/2 15:47
 */
@Service
public class PostStatisticsService {
    @Autowired
    PostStatisticsMapper postStatisticsMapper;

    /** 按部门统计 */
    public List<ChartData> getByDepart() {
        List<ChartData> chartData = postStatisticsMapper.selectByDepart();
        return chartData.stream().peek(item -> item.setTitle(Optional.ofNullable(item.getTitle()).orElse("其他"))).collect(Collectors.toList());
    }

    /** 按发布用户统计 */
    public List<ChartData> getByUser() {
        List<ChartData> chartData = postStatisticsMapper.selectByUser();
        return chartData.stream().peek(item -> item.setTitle(Optional.ofNullable(item.getTitle()).orElse("其他"))).collect(Collectors.toList());
    }

    /** 按类型统计 */
    public List<ChartData> getByType() {
        List<ChartData> chartData = postStatisticsMapper.selectByType();
        chartData.forEach(item -> {
            String title;
            switch (item.getTitle()) {
                case "1":
                    title = "文博新闻";
                    break;
                case "3":
                    title = "重大活动";
                    break;
                case "6":
                    title = "文物课堂";
                    break;
                case "7":
                    title = "学术研究";
                    break;
                default:
                    title = "其他";
            }
            item.setTitle(title);
        });
        return chartData;
    }

    /** 按状态统计 */
    public List<ChartData> getByStatus() {
        List<ChartData> chartData = postStatisticsMapper.selectByStatus();
        chartData.forEach(item -> {
            String title;
            switch (item.getTitle()) {
                case "1":
                    title = "审核通过";
                    break;
                case "2":
                    title = "审核不通过";
                    break;
                case "3":
                    title = "待审核";
                    break;
                case "4":
                    title = "草稿";
                    break;
                default:
                    title = "其他";
            }
            item.setTitle(title);
        });
        return chartData;
    }


    /** 文章总数 */
    public Integer getTotal() {
        return postStatisticsMapper.selectTotal();
    }

    /** 最近新增 */
    public Integer getRecentlyAdded() {
        return postStatisticsMapper.selectRecentlyAdded();

    }

    /** 今日新增 */
    public Integer getTodayAdded() {
        return postStatisticsMapper.selectTodayAdded();
    }

    /** 发布文章最多的部门 */
    public String getTopCount() {
        return postStatisticsMapper.selectTopCount();
    }

    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("total", getTotal());
        map.put("recentlyAdded", getRecentlyAdded());
        map.put("todayAdded", getTodayAdded());
        map.put("topCount", getTopCount());

        map.put("departChart", getByDepart());
        map.put("userChart", getByUser());
        map.put("typeChart", getByType());
        map.put("statusChart", getByStatus());
        return map;
    }
}
