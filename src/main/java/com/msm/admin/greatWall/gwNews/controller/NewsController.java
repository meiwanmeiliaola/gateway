package com.msm.admin.greatWall.gwNews.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.common.enums.ErrorInfo;
import com.msm.admin.greatWall.Info.entity.GwScenicInfo;
import com.msm.admin.greatWall.gwNews.entity.GwNews;
import com.msm.admin.greatWall.gwNews.service.NewsService;
import com.msm.admin.utils.DateUtil;
import com.msm.admin.utils.ExceptionThrower;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zxy
 * @date 2022/2/11 17:01
 */
@RestController
@RequestMapping(value = "/gwNews")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PutMapping
    public GwNews save(@RequestBody GwNews news){
        QueryWrapper<GwNews> wrapper=new QueryWrapper<>();
        List<Object> list;
        wrapper.eq("name",news.getName());
        if (StringUtils.isEmpty(news.getCreateDate())){
            news.setCreateDate(DateUtil.getNowTime());
        }
        if (news.getStatus()==null){
            news.setStatus(0);
        }

        if (StringUtils.isNotEmpty(news.getId())){
            wrapper.ne("id",news.getId());
            list= newsService.listObjs(wrapper);
        }else {
            list=newsService.listObjs(wrapper);
        }

        boolean flag = list.size() ==0;
        if (flag){
            newsService.saveOrUpdate(news);
        }else {
            ExceptionThrower.of(ErrorInfo.TITLE_EXIST);
        }

        return news;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id){
        newsService.removeById(id);
    }

    @GetMapping(value = "/{id}")
    public GwNews getById(@PathVariable String id){
        GwNews gwNews = newsService.getById(id);
        return gwNews;
    }

    @GetMapping(value = "/list")
    public IPage page(Page<GwNews> page,GwNews gwNews){
        return newsService.pageSearch(page,gwNews);
    }


    @GetMapping("/QueryByName")
    public String getByName(String name){
        return newsService.getByName(name);
    }




}
