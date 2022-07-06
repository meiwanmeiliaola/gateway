package com.msm.admin.modules.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.monitor.entity.SysLog;
import com.msm.admin.modules.monitor.mapper.SysLogMapper;
import com.msm.admin.modules.monitor.service.SysLogService;
import com.msm.admin.utils.SubjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Description: SysLog
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {


    @Override
    public IPage pageSearch(Page<SysLog> page, SysLog log) {
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();

        if (log != null) {
            if (!StringUtils.isEmpty(log.getType())) {
                queryWrapper.eq("type", log.getType());
            }
        }

        queryWrapper.orderByDesc("record_date");
        Page<SysLog> logPage = super.page(page, queryWrapper);
        return logPage;
    }
}
