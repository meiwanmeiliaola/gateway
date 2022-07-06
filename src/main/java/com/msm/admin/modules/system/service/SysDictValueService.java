package com.msm.admin.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.greatWall.gwCulture.entity.GwCulture;
import com.msm.admin.modules.system.entity.SysDictValue;

/**
 * @Description: area
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface SysDictValueService extends IService<SysDictValue> {

    IPage pageSearch(Page<SysDictValue> page, SysDictValue dictValue);

}
