package com.msm.admin.modules.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.monitor.entity.SysException;
import com.msm.admin.modules.monitor.entity.SysLog;

/**
 * @Description: SysLog
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface SysExceptionService extends IService<SysException> {

    IPage pageSearch(Page<SysException> page, SysException exception);
}
