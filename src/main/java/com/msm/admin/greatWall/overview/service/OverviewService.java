package com.msm.admin.greatWall.overview.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.greatWall.overview.entity.GwOverview;

import java.util.List;

/**
 * @author zxy
 * @date 2022/2/11 11:32
 */
public interface OverviewService extends IService<GwOverview> {
    Page pageSearch(Page<GwOverview> page, GwOverview gwOverview);

    List<GwOverview> selectListByPid(GwOverview gwOverview);

    void setStatus(String id, Integer status);

    List<GwOverview> selectListByAffiliation(QueryWrapper<GwOverview> queryWrapper);
}
