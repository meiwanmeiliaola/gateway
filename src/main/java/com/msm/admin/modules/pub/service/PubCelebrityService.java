package com.msm.admin.modules.pub.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.common.entity.Years;
import com.msm.admin.modules.common.service.YearsService;
import com.msm.admin.modules.info.entity.Celebrity;
import com.msm.admin.modules.info.service.CelebrityService;
import com.msm.admin.modules.system.service.SysDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author quavario@gmail.com
 * @date 2019/12/18 17:15
 */
@Service
public class PubCelebrityService {
    @Autowired
    private CelebrityService celebrityService;

    @Autowired
    private YearsService yearsService;


    @Autowired
    private PubDictValueService dictValueService;

    @Autowired
    private SysDepartService departService;


    private static final String[] QUERY_LIST_FIELD = {
            "id", "name", "avatar",
    };

    private static final String[] QUERY_ONE_FIELD = {
            "name", "avatar", "content",
    };


    private static final String[] RELIC_INFOS_ID = {
            "e3bcefcf527941c3af9fb3b37d9eed79",     // 文物年代
    };

    @Cacheable(value = "pubCelebrity", keyGenerator = "cacheKeyGenerator", unless = "#result.records.size() == 0")
    public IPage pageSearch(Page<Celebrity> page, Celebrity celebrity) {
        QueryWrapper<Celebrity> queryWrapper = new QueryWrapper<>();

        if (celebrity != null) {
            // 按文物年代查询
            if (!StringUtils.isEmpty(celebrity.getYearsId())) {
                queryWrapper.eq("years_id", celebrity.getYearsId());
            }

            if (!StringUtils.isEmpty(celebrity.getName())) {
                queryWrapper.like("name", celebrity.getName());
            }
        }

        queryWrapper.orderByDesc("update_date", "create_date");
        queryWrapper.select(QUERY_LIST_FIELD).eq("status", 1);
        Page<Celebrity> celebrityPage = celebrityService.page(page, queryWrapper);
        return celebrityPage;
    }

    public Celebrity getById(String id) {
        QueryWrapper<Celebrity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(QUERY_ONE_FIELD).eq("id", id).eq("status", 1);
        return celebrityService.getOne(queryWrapper);
    }

}
