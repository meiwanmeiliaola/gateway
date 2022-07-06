package com.msm.admin.modules.analysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msm.admin.modules.analysis.entity.StatView;
import com.msm.admin.modules.analysis.entity.VisitView;
import com.msm.admin.modules.statistics.entity.ChartData;

import java.util.Date;
import java.util.List;

/**
 * @Description: 访问分析Mapper
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface VisitViewMapper extends BaseMapper<VisitView> {
    int selectUserViewCountByNearDay(Date date);

    List<ChartData> selectByDevice();
}
