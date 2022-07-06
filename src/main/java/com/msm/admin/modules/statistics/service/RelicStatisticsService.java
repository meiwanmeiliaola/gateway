package com.msm.admin.modules.statistics.service;

import com.msm.admin.modules.statistics.entity.ChartData;
import com.msm.admin.modules.statistics.mapper.RelicStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author quavario@gmail.com
 * @date 2020/1/2 15:47
 */
@Service
public class RelicStatisticsService {
    @Autowired
    private RelicStatisticsMapper relicStatisticsMapper;

    public List<ChartData> getByArea() {
        List<ChartData> chartData = relicStatisticsMapper.selectByArea();
        return chartData;
    }

    public List<ChartData> getByDepart() {
        List<ChartData> chartData = relicStatisticsMapper.selectByDepart();
        return chartData;
    }

    public Map getBasicInfo() {
        Map map = new HashMap();
        Integer monthRise = relicStatisticsMapper.selectMonthRise();
        Integer total = relicStatisticsMapper.selectTotal();
        Map topDep = relicStatisticsMapper.selectTopCountDep();
        map.put("monthRise", monthRise);
        map.put("total", total);
        map.put("topDep", topDep);
        return map;
    }

    public Object getRelicInfoPipe() {
        List<ChartData> chartData = relicStatisticsMapper.selectInfoStat();
        Map<String, List<ChartData>> map = chartData.stream().collect(Collectors.groupingBy(ChartData::getType));
        return map;
    }

    public Object getRelicInfoBar() {
        List<ChartData> chartData = relicStatisticsMapper.selectInfoStat2();
        Map<String, List<ChartData>> map = chartData.stream().sorted(Comparator.comparing(ChartData::getType)).sorted(Comparator.comparing(ChartData::getValue).reversed()).collect(Collectors.groupingBy(ChartData::getType));
        return map;
    }

}
