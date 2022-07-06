package com.msm.admin.modules.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.app.entity.Ruins;

/**
 * @Description: Ruins
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface RuinsService extends IService<Ruins> {

    IPage pageSearch(Page<Ruins> page, Ruins ruins);
    void setStatus(String id, Integer status);
    IPage reviewSearch(Page<Ruins> page, Ruins ruins);
}
