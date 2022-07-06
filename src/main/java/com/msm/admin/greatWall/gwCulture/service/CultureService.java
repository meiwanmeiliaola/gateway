package com.msm.admin.greatWall.gwCulture.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.greatWall.gwCulture.entity.GwCulture;

/**
 * @author zxy
 * @date 2022/2/11 17:38
 */
public interface CultureService extends IService<GwCulture> {
    Page pageSearch(Page<GwCulture> page, GwCulture culture);

    void setStatus(String id, Integer status);

    Page<GwCulture> pageGwCulture(Page<GwCulture> page, GwCulture gwCulture);
}
