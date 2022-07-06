package com.msm.admin.modules.pub.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.app.entity.Explore;
import com.msm.admin.modules.app.service.ExploreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author quavario@gmail.com
 * @date 2019/12/18 17:15
 */
@Service
public class PubExploreService {
    @Autowired
    private ExploreService exploreService;



    private static final String[] QUERY_LIST_FIELD = {
            "id", "title", "thumb", "url", "type"
    };

    public IPage pageSearch(Page<Explore> page, Explore explore) {
        QueryWrapper<Explore> queryWrapper = new QueryWrapper<>();
        if (explore != null) {
            if (!StringUtils.isEmpty(explore.getType())) {
                queryWrapper.eq("type", explore.getType());
            }
        }
        queryWrapper.eq("status", 1).select(QUERY_LIST_FIELD);
        queryWrapper.orderByDesc("update_date", "create_date");

        IPage<Explore> exploreIPage = exploreService.page(page, queryWrapper);
        return exploreIPage;
    }


    public Explore getById(String id) {
        QueryWrapper<Explore> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(QUERY_LIST_FIELD).eq("id", id).eq("status", 1);
        return exploreService.getOne(queryWrapper);
    }

}
