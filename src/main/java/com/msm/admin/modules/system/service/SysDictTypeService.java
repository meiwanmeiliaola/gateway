package com.msm.admin.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.system.entity.SysDictType;
import com.msm.admin.modules.system.entity.SysDictValue;

import java.util.List;

/**
 * @Description: area
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface SysDictTypeService extends IService<SysDictType> {
    IPage pageSearch(Page<SysDictType> page, SysDictType dictType);
}
