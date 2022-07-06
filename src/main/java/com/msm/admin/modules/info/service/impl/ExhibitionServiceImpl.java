package com.msm.admin.modules.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.info.entity.Exhibition;
import com.msm.admin.modules.info.entity.Exhibition;
import com.msm.admin.modules.info.mapper.ExhibitionMapper;
import com.msm.admin.modules.info.service.ExhibitionService;
import com.msm.admin.modules.system.service.SysUserService;
import com.msm.admin.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: Exhibition
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
public class ExhibitionServiceImpl extends ServiceImpl<ExhibitionMapper, Exhibition> implements ExhibitionService {

    @Autowired
    private SysUserService userService;

    @Override
    public List<Exhibition> list(Wrapper<Exhibition> queryWrapper) {
        List<Exhibition> list = super.list(queryWrapper);
        return list;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "pubExhibition", key = "'getById:' + #entity.id", condition = "#entity.id != null"),
                    // 当添加或编辑的展览为临展时删除缓存
                    @CacheEvict(cacheNames = "pubExhibition:temporaryExhibitionList", allEntries = true, condition = "#entity.type == 2"),
                    @CacheEvict(cacheNames = "pubExhibition:panoPageSearch", allEntries = true),
                    @CacheEvict(cacheNames = "pubExhibition:pageSearch", allEntries = true),
            }
    )
    public boolean saveOrUpdate(Exhibition entity) {
        if (!SubjectUtils.isAdmin()) {
            if (entity.getId() == null) {
                entity.setStatus(3);
            } else {
                Exhibition dbExhibition = getById(entity.getId());

                if (dbExhibition.getStatus() != 1) {
                    entity.setStatus(3);
                }
            }
        }
        return super.saveOrUpdate(entity);
    }



    @Override
    @Caching(
        evict = {
            @CacheEvict(value = {"pubExhibition"}, key = "'getById:' + #id"),
            // 删除展览时无法判断其类型，只能全部删除temporaryExhibitionList缓存
            @CacheEvict(cacheNames = "pubExhibition:temporaryExhibitionList", allEntries = true),
            @CacheEvict(cacheNames = "pubExhibition:panoPageSearch", allEntries = true),
            @CacheEvict(cacheNames = "pubExhibition:pageSearch", allEntries = true),
        }
    )
    public boolean removeById(Serializable id) {
        Boolean removed;
        if (SubjectUtils.isAdmin()) {
            removed = super.removeById(id);
        } else {
            removed = remove(Wrappers.<Exhibition>lambdaQuery().eq(Exhibition::getId, id).eq(Exhibition::getCreateBy, SubjectUtils.getCurrentUser().getId()));
        }
        return removed;
    }
    
    
    @Override
    public IPage pageSearch(Page<Exhibition> page, Exhibition exhibition) {
        QueryWrapper<Exhibition> queryWrapper = new QueryWrapper<>();

        if (!SubjectUtils.isAdmin()) {
//            queryWrapper.eq("create_by", SubjectUtils.getCurrentUser().getId());
            System.out.println(SubjectUtils.getCurrentUser());
            queryWrapper.eq("dep_id", SubjectUtils.getCurrentUser().getDepId());
        }

        if (!StringUtils.isEmpty(exhibition.getTitle())) {
            queryWrapper.like("title", exhibition.getTitle());
        }

        if (!StringUtils.isEmpty(exhibition.getType())) {
            queryWrapper.eq("type", exhibition.getType());
        }

        queryWrapper.orderByDesc("update_date", "create_date");
        Page<Exhibition> exhibitionPage = super.page(page, queryWrapper);
        List<Exhibition> records = exhibitionPage.getRecords();

        records.forEach(item -> {
            // 设置创建人
            String userId = item.getCreateBy();
            item.setCreateBy(userService.getNameById(userId));
        });

        return exhibitionPage;
    }
}
