package com.msm.admin.greatWall.gwNews.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.greatWall.gwNews.entity.GwNews;
import com.msm.admin.greatWall.gwNews.mapper.NewsMapper;

/**
 * @author zxy
 * @date 2022/2/11 17:02
 */
public interface NewsService extends IService<GwNews> {
    IPage pageSearch(Page<GwNews> page, GwNews gwNews);

    String getByName(String name);
}
