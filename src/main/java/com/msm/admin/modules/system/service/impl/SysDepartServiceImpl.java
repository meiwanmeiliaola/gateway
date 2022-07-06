package com.msm.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.system.entity.SysDepart;
import com.msm.admin.modules.system.mapper.SysDepartMapper;
import com.msm.admin.modules.system.service.SysDepartService;
import com.msm.admin.utils.SubjectUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @Description: 单位
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Service
public class SysDepartServiceImpl extends ServiceImpl<SysDepartMapper, SysDepart> implements SysDepartService {

    private static final String[] QUERY_LIST_FIELD = {
            "id", "name", "sort", "thumb", "pano", "create_date", "area_id"
    };
    
    @Override
    public String getNameById(String id) {
        SysDepart one = getOne(Wrappers.<SysDepart>lambdaQuery().eq(SysDepart::getId, id).select(SysDepart::getName));
        if (one != null) {
            return one.getName();
        }
        return null;

    }

    @Override
    public IPage pageSearch(Page<SysDepart> page, SysDepart depart) {
        depart.setParentId("1");
        QueryWrapper<SysDepart> queryWrapper = new QueryWrapper<>();
       

        if (!SubjectUtils.isAdmin()) {
            queryWrapper.eq("create_by", SubjectUtils.getCurrentUser().getId());
        }

        // 按名称查询
        if (!StringUtils.isEmpty(depart.getName())) {
            queryWrapper.like("name", depart.getName());
        }

        // 类别查询 是否是革命文物单位
        if (depart.getType() != null) {
            queryWrapper.eq("type", depart.getType());
        }

        queryWrapper.select(QUERY_LIST_FIELD);
        queryWrapper.orderByDesc("update_date", "create_date");
        Page<SysDepart> departPage = super.page(page, queryWrapper);

        return departPage;
    }

    @Override
    public SysDepart getById(Serializable id) {
        SysDepart depart;
        if (!SubjectUtils.isAdmin()) {
            depart = getOne(Wrappers.<SysDepart>lambdaQuery().eq(SysDepart::getId, id).eq(SysDepart::getCreateBy, SubjectUtils.getCurrentUser().getId()));
        } else {
            depart = super.getById(id);
        }

        return depart;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "pubRelic", key = "'info'")
    })
    public boolean removeById(Serializable id) {
        Boolean removed;
        if (SubjectUtils.isAdmin()) {
            removed = super.removeById(id);
        } else {
            removed = remove(Wrappers.<SysDepart>lambdaQuery().eq(SysDepart::getId, id).eq(SysDepart::getCreateBy, SubjectUtils.getCurrentUser().getId()));
        }
        return removed;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "pubRelic", key = "'info'")
    })
    public boolean saveOrUpdate(SysDepart entity) {

        entity.setParentId("1");
        if (!SubjectUtils.isAdmin()) {
            if (entity.getId() == null) {
                entity.setStatus(3);
            } else {
                SysDepart dbSysDepart = getById(entity.getId());
                entity.setStatus(dbSysDepart.getStatus());
            }
        }

        return super.saveOrUpdate(entity);
    }

}
