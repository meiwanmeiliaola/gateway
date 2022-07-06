package com.msm.admin.modules.analysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.analysis.entity.StatView;
import com.msm.admin.modules.statistics.entity.ChartData;

import java.util.List;

/**
 * @Description: View
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface StatViewService extends IService<StatView> {
    public List<ChartData> getPopRelic(Integer type);

    List<ChartData> selectPopRelicCatg();

}
