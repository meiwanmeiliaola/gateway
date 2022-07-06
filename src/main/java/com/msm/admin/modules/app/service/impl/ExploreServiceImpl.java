package com.msm.admin.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.app.entity.Explore;
import com.msm.admin.modules.app.mapper.ExploreMapper;
import com.msm.admin.modules.app.service.ExploreService;
import com.msm.admin.modules.system.service.SysUserService;
import com.msm.admin.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: Explore
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
public class ExploreServiceImpl extends ServiceImpl<ExploreMapper, Explore> implements ExploreService {

    @Autowired
    private SysUserService userService;
    @Override
    public boolean saveOrUpdate(Explore entity) {

        // 非管理员不能设置status字段
        if (!SubjectUtils.isAdmin()) {
            // 添加默认设置status = 3
            // 更新设置为原来的状态
            entity.setStatus(3);
            /*if (entity.getId() == null) {
                entity.setStatus(3);
            } else {
                Explore dbExplore = getById(entity.getId());
                if (dbExplore == null) {
                    return false;
                }
                entity.setStatus(dbExplore.getStatus());
            }*/
        }
        return super.saveOrUpdate(entity);
    }

    @Override
    public IPage pageSearch(Page<Explore> page, Explore explore) {
        QueryWrapper<Explore> queryWrapper = new QueryWrapper<>();

        if (!SubjectUtils.isAdmin()) {
//            queryWrapper.eq("create_by", SubjectUtils.getCurrentUser().getId());
            queryWrapper.eq("dep_id", SubjectUtils.getCurrentUser().getDepId());
        }

        if (explore != null) {
            if (!StringUtils.isEmpty(explore.getTitle())) {
                queryWrapper.like("title", explore.getTitle());
            }

            if (!StringUtils.isEmpty(explore.getType())) {
                queryWrapper.eq("type", explore.getType());
            }
        }

        queryWrapper.orderByDesc("update_date", "create_date");
        Page<Explore> explorePage = super.page(page, queryWrapper);
        List<Explore> records = explorePage.getRecords();
        records.forEach(item -> {
            // 设置创建人
            String userId = item.getCreateBy();
            item.setCreateBy(userService.getNameById(userId));
        });
        return explorePage;
    }

    /**
     * 普通用户拥有查看权限
     * @param id
     * @return
     */
    @Override
    public Explore getById(Serializable id) {
        Explore explore;
        if (SubjectUtils.isAdmin()) {
            explore = super.getById(id);
        } else {
            explore = getOne(Wrappers.<Explore>lambdaQuery().eq(Explore::getId, id).eq(Explore::getCreateBy, SubjectUtils.getCurrentUser().getId()));
        }
        return explore;
    }

    @Override
    public boolean removeById(Serializable id) {
        Boolean removed;
        if (SubjectUtils.isAdmin()) {
           removed = super.removeById(id);
        } else {
            removed = super.remove(Wrappers.<Explore>lambdaQuery().eq(Explore::getId, id).eq(Explore::getCreateBy, SubjectUtils.getCurrentUser().getId()));
        }
        return removed;
    }

    /**
     * 探索发现审核
     * @param id 带审核id
     * @param status 审核状态
     */
    @Override
    public void setStatus(String id, Integer status) {
        Explore explore = getById(id);
        if (explore != null) {
            explore.setStatus(status);
            updateById(explore);
        }
    }

    /**
     * 查看待审核explore
     * @param page 参数
     * @param explore 实体
     * @return explore 列表
     */
    @Override
    public IPage reviewSearch(Page<Explore> page, Explore explore) {

        QueryWrapper<Explore> queryWrapper = new QueryWrapper<>();

        if (explore != null) {
            // 按名称查询
            if (!StringUtils.isEmpty(explore.getTitle())) {
                queryWrapper.like("title", explore.getTitle());

            }
        }

        queryWrapper.eq("status", 2).or().eq("status", 3);
        queryWrapper.orderByDesc("status", "update_date");

        Page<Explore> explorePage = super.page(page, queryWrapper);

        return explorePage;
    }
}
