package com.msm.admin.modules.pub.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.common.entity.TreeData;
import com.msm.admin.modules.common.entity.Years;
import com.msm.admin.modules.common.service.YearsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author quavario@gmail.com
 * @date 2019/12/18 17:15
 * // TODO 年代删除策略
 */
@Service
public class PubYearsService {
    @Autowired
    private YearsService yearsService;


    private static final String[] QUERY_ONE_FIELD = {
            "id", "name", "image", "parent_id", "sort", "type", "icon", "content", "banner"
    };
    private static final String[] QUERY_LIST_FIELD = {
            "id", "name", "image", "parent_id", "sort", "type", "icon", "content"
    };

    @Cacheable(value = "pubYears", keyGenerator = "cacheKeyGenerator")
    public IPage pageSearch(Page<Years> page, Years years) {
        QueryWrapper<Years> queryWrapper = new QueryWrapper<>();
        if (years != null) {
            if (!StringUtils.isEmpty(years.getName())) {
                queryWrapper.like("name", years.getName());
            }
            if (!StringUtils.isEmpty(years.getType())) {
                queryWrapper.eq("type", years.getType());
            }
        }
        queryWrapper.eq("status", 1).select(QUERY_LIST_FIELD);
        IPage<Years> yearsIPage = yearsService.page(page, queryWrapper);
        return yearsIPage;
    }

    @Cacheable(value = "pubYears", keyGenerator = "cacheKeyGenerator")
    public List<Years> tree() {
        QueryWrapper<Years> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("status", 1).select(QUERY_LIST_FIELD).orderByAsc("sort");
        List<Years> list = yearsService.list(queryWrapper);
        List<Years> treeList = TreeData.toTreeList(list);
        return treeList;
    }

    @Cacheable(value = {"pubYears"}, key = "'getById:' + #id")
    public Years getById(String id) {
        QueryWrapper<Years> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(QUERY_ONE_FIELD).eq("id", id).eq("status", 1);
        return yearsService.getOne(queryWrapper);
    }

    @Cacheable(value = "pubYears", keyGenerator = "cacheKeyGenerator")
    public List<Years> list() {
        QueryWrapper<Years> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("status", 1).select(QUERY_LIST_FIELD).eq("type", 2).orderByAsc("sort");
        List<Years> list = yearsService.list(queryWrapper);
        return list;
    }
}
