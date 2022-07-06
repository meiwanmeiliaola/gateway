package com.msm.admin.modules.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.app.entity.Explore;

/**
 * @Description: Explore
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface ExploreService extends IService<Explore> {

    IPage pageSearch(Page<Explore> page, Explore explore);

    void setStatus(String id, Integer status);
    IPage reviewSearch(Page<Explore> page, Explore explore);
}
