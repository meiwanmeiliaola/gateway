package com.msm.admin.greatWall.gwNews.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.greatWall.gwNews.entity.GwNews;
import com.msm.admin.greatWall.gwNews.mapper.NewsMapper;
import com.msm.admin.greatWall.gwNews.service.NewsService;
import com.msm.admin.greatWall.gwRoam.entity.GwRoam;
import com.msm.admin.modules.info.entity.Celebrity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxy
 * @date 2022/2/11 17:02
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, GwNews> implements NewsService {


    @Autowired
    private NewsMapper newsMapper;

    @Override
    public IPage pageSearch(Page<GwNews> page, GwNews gwNews) {
        QueryWrapper<GwNews> wrapper=new QueryWrapper<>();
        if (gwNews !=null){
            if (StringUtils.isNotEmpty(gwNews.getName())){
                wrapper.like("name",gwNews.getName());
            }
            if (gwNews.getStatus() !=null){
                wrapper.eq("status",gwNews.getStatus());
            }
        }
        wrapper.orderByDesc("create_date");
        Page<GwNews> gwNewsPage = newsMapper.selectPage(page, wrapper);
        return gwNewsPage;
    }

    @Override
    public String getByName(String name) {
        String result = null;

        GwNews gwNews=new GwNews();
        gwNews.setName(name);

        LambdaQueryWrapper<GwNews> queryWrapper = Wrappers.lambdaQuery();
        // 校验姓名
        if (!StringUtils.isEmpty(name)) {
            List<GwNews> list = list(queryWrapper
                    .eq(GwNews::getName, gwNews.getName()).select(GwNews::getName));
            return list.size() == 0 ? null : "该名称已经存在，请检查名称";
        }
        return result;
    }
}
