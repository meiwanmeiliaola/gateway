package com.msm.admin.greatWall.gwArmchair.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.greatWall.gwArmchair.entity.Armchair;

public interface ArmchairService extends IService<Armchair> {
    IPage<Armchair> pageSearch(Page<Armchair> page, Armchair armchair);
}
