package com.msm.admin.greatWall.gwNum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.greatWall.gwNum.entity.GwNum;

/**
 * ????(GwNum)表服务接口
 *
 * @author makejava
 * @since 2022-02-11 14:56:54
 */
public interface GwNumService extends IService<GwNum> {

    IPage pageSearch(Page<GwNum> page, GwNum gwNum);

    void steStatus(String id, Integer status);
}

