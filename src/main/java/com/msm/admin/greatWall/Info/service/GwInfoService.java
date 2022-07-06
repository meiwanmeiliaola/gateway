package com.msm.admin.greatWall.Info.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.greatWall.Info.entity.GwScenicInfo;

/**
 * @author zxy
 * @date 2022/2/11 16:43
 */
public interface GwInfoService extends IService<GwScenicInfo> {
    IPage pageSearch(Page<GwScenicInfo> page, GwScenicInfo info);

    void setStatus(String id, Integer status);
}
