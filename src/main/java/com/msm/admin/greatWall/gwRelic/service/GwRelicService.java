package com.msm.admin.greatWall.gwRelic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.greatWall.gwRelic.entity.GwRelic;

import java.util.List;

/**
 * @author zxy
 * @date 2022/2/9 16:37
 */
public interface GwRelicService extends IService<GwRelic> {
    Page pageSearch(Page<GwRelic> page, GwRelic relic);

    void setStatus(String id, Integer status);

    Page<GwRelic> recommonList(Page<GwRelic> page,GwRelic relic);


}
