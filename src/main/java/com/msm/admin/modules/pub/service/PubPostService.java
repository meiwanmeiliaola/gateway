package com.msm.admin.modules.pub.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.common.service.YearsService;
import com.msm.admin.modules.info.entity.Post;
import com.msm.admin.modules.info.service.PostService;
import com.msm.admin.modules.system.entity.SysDepart;
import com.msm.admin.modules.system.service.SysDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author quavario@gmail.com
 * @date 2019/12/18 17:15
 */
@Service
public class PubPostService {
    @Autowired
    private PostService postService;

    @Autowired
    private YearsService yearsService;

    @Autowired
    private SysDepartService departService;

    private static final String[] QUERY_LIST_FIELD = {
            "id", "title", "thumb", "create_date", "sub_title", "dep_id"
    };

    private static final String[] QUERY_ONE_FIELD = {
            "id", "title", "sub_title", "thumb", "content", "create_date", "dep_id"
    };


    private static final String[] RELIC_INFOS_ID = {
            "e3bcefcf527941c3af9fb3b37d9eed79",     // 文物年代
    };

    @Cacheable(value = "pubPost", keyGenerator = "cacheKeyGenerator", unless = "#result.records.size() == 0")
    public IPage pageSearch(Page<Post> page, Post post) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(post.getTitle())) {
            queryWrapper.like("title", post.getTitle());
        }

        if (!StringUtils.isEmpty(post.getType())) {
            queryWrapper.eq("type", post.getType());
        }

        if (!StringUtils.isEmpty(post.getDepId())) {
            queryWrapper.eq("dep_id", post.getDepId());
        }

        if (!StringUtils.isEmpty(post.getHoldingDate())) {
            queryWrapper.eq("holding_date", post.getHoldingDate());
        }

        queryWrapper.select(QUERY_LIST_FIELD).eq("status", 1);
        queryWrapper.orderByDesc("create_date", "update_date");

        Page<Post> postPage = postService.page(page, queryWrapper);

        postPage.getRecords().forEach(item -> {
            item.setDepId(departService.getNameById(item.getDepId()));
        });
        System.out.println(postPage.getRecords().size());
        return postPage;
    }

    @Cacheable(value = {"pubPost"}, key = "'getById:' + #id", unless = "#result == null")
    public Post getById(String id) {

        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();

        queryWrapper.select(QUERY_ONE_FIELD).eq("id", id).eq("status", 1);
        Post post = postService.getOne(queryWrapper);
        Optional.ofNullable(post.getDepId()).ifPresent(depId -> post.setDepId(departService.getNameById(depId)));

        return post;
    }

}
