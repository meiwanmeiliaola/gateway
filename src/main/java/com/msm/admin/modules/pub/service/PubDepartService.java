package com.msm.admin.modules.pub.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.system.entity.SysDepart;
import com.msm.admin.modules.system.service.SysDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author quavario@gmail.com
 * @date 2019/12/18 17:15
 */
@Service
public class PubDepartService {
    @Autowired
    private SysDepartService departService;

    private static final String[] QUERY_LIST_FIELD = {
            "id", "name", "thumb", "pano_thumb", "area_id", "pano", "sort", "type"
    };

    private static final String[] QUERY_ONE_FIELD = {
            "id", "name", "thumb", "pano_thumb", "area_id", "pano", "content", "address", "route", "open_time", "route", "phone"
    };

    private static final String[] RELIC_INFOS_ID = {
            "e3bcefcf527941c3af9fb3b37d9eed79",     // 文物年代
    };

    public IPage pageSearch(Page<SysDepart> page, SysDepart depart) {
        QueryWrapper<SysDepart> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(depart.getName())) {
            queryWrapper.like("name", depart.getName());
        }

        if (!StringUtils.isEmpty(depart.getAreaId())) {
            queryWrapper.eq("area_id", depart.getAreaId());
        }

        if (depart.getType() != null) {
            queryWrapper.eq("type", depart.getType());
        }
        queryWrapper.orderByAsc("sort");
        queryWrapper.eq("status", 1).select(QUERY_LIST_FIELD);
        IPage<SysDepart> departIPage = departService.page(page, queryWrapper);
        return departIPage;
    }

    public SysDepart getById(String id) {
        QueryWrapper<SysDepart> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(QUERY_ONE_FIELD).eq("id", id).eq("status", 1);
        return departService.getOne(queryWrapper);
    }

}
