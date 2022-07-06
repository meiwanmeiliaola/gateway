package com.msm.admin.modules.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.monitor.entity.SysException;
import com.msm.admin.modules.monitor.entity.SysLog;
import com.msm.admin.modules.monitor.mapper.SysExceptionMapper;
import com.msm.admin.modules.monitor.mapper.SysLogMapper;
import com.msm.admin.modules.monitor.service.SysExceptionService;
import com.msm.admin.modules.monitor.service.SysLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
public class SysExceptionServiceImpl extends ServiceImpl<SysExceptionMapper, SysException> implements SysExceptionService {

    @Override
    public IPage pageSearch(Page<SysException> page, SysException exception) {
        Page<SysException> page1 = page(page, Wrappers.<SysException>lambdaQuery().orderByDesc(SysException::getRecordDate));
        return page1;
    }
}
