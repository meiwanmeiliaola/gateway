package com.msm.admin.modules.analysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msm.admin.modules.analysis.entity.StatView;
import com.msm.admin.modules.statistics.entity.ChartData;

import java.util.List;

/**
 * @Description: log
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface StatViewMapper extends BaseMapper<StatView> {
    List<ChartData> selectPopRelic(Integer type);

    List<ChartData> selectPopRelicCatg();
}
