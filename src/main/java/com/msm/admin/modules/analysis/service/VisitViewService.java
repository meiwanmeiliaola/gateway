package com.msm.admin.modules.analysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.analysis.entity.StatView;
import com.msm.admin.modules.analysis.entity.VisitView;
import com.msm.admin.modules.statistics.entity.ChartData;

import java.util.List;

/**
 * @Description: 访问分析service
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface VisitViewService extends IService<VisitView> {
    /**
     * 访问量
     * @param nearDay 统计的最近访问天数
     * @return 近nearDay天访问量
     */
    Object getPageViewCountByNearDay(Integer nearDay );


    /**
     * @param nearDay 近期天数
     * @return 近nearDay的访客数
     */
    Object getUserViewCountByNearDay(Integer nearDay);

    List<ChartData> selectByDevice();
}
