package com.msm.admin.modules.analysis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.analysis.entity.StatView;
import com.msm.admin.modules.analysis.mapper.StatViewMapper;
import com.msm.admin.modules.analysis.service.StatViewService;
import com.msm.admin.modules.statistics.entity.ChartData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: View
 * @Author: quavario
 * @Date: 2019-05-30
 * @Version: V1.0
 */
@Service
public class StatViewServiceImpl extends ServiceImpl<StatViewMapper, StatView> implements StatViewService {
    @Autowired
    private StatViewMapper viewMapper;

    public List<ChartData> getPopRelic(Integer type) {
        List<ChartData> chartData = viewMapper.selectPopRelic(type);
        return chartData;
    }

    @Override
    public List<ChartData> selectPopRelicCatg() {
        return viewMapper.selectPopRelicCatg();
    }

}
