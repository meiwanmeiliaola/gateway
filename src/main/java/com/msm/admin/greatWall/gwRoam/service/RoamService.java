package com.msm.admin.greatWall.gwRoam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.greatWall.gwNum.entity.GwNum;
import com.msm.admin.greatWall.gwRoam.entity.GwRoam;

/**
 * @author zxy
 * @date 2022/2/18 10:06
 */
public interface RoamService extends IService<GwRoam> {
    Page pageSearch(Page<GwRoam> page, GwRoam roam);

}
