package com.msm.admin.greatWall.gwPoetry.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.greatWall.gwNum.entity.GwNum;
import com.msm.admin.greatWall.gwPoetry.entity.PoetryEntity;

public interface PoetryService extends IService<PoetryEntity> {
    IPage pageSearch(Page<PoetryEntity> page, PoetryEntity poetryEntity);
}
