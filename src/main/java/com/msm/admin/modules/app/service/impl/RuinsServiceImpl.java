package com.msm.admin.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.app.entity.Ruins;
import com.msm.admin.modules.app.entity.Ruins;
import com.msm.admin.modules.app.mapper.RuinsMapper;
import com.msm.admin.modules.app.service.RuinsService;
import com.msm.admin.modules.system.service.SysUserService;
import com.msm.admin.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @Description: ruins
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
public class RuinsServiceImpl extends ServiceImpl<RuinsMapper, Ruins> implements RuinsService {

    @Autowired
    private SysUserService userService;
    @Override
    public List<Ruins> list(Wrapper<Ruins> queryWrapper) {
        List<Ruins> list = super.list(queryWrapper);
        return list;
    }

    @Override
    public boolean saveOrUpdate(Ruins entity) {

        // 非管理员不能设置status字段
        if (!SubjectUtils.isAdmin()) {
            // 添加默认设置status = 3
            // 更新设置为原来的状态
            entity.setStatus(3);

            /*if (entity.getId() == null) {
            } else {
                Ruins dbRuins = getById(entity.getId());
                if (dbRuins == null) {
                    log.error("更新ID错误");
                    return false;
                }
                entity.setStatus(dbRuins.getStatus());
            }*/
        }
        return super.saveOrUpdate(entity);
    }

    @Override
    public IPage pageSearch(Page<Ruins> page, Ruins ruins) {
//        QueryWrapper<Ruins> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<Ruins> queryWrapper = new LambdaQueryWrapper<>();

        if (!SubjectUtils.isAdmin()) {
            queryWrapper.eq(Ruins::getCreateBy, SubjectUtils.getCurrentUser().getId());
        }

        /*if (!StringUtils.isEmpty(ruins.getTitle())) {
            queryWrapper.like(Ruins::getTitle, ruins.getTitle());
        }


        if (!StringUtils.isEmpty(ruins.getYearsId())) {
            queryWrapper.eq(Ruins::getYearsId, ruins.getYearsId());
        }*/

        // 标题检索
        Optional.ofNullable(ruins.getTitle()).ifPresent(title -> {
            queryWrapper.like(Ruins::getTitle, title);
        });

        // 年代检索
        Optional.ofNullable(ruins.getYearsId()).ifPresent(yearsId -> {
            queryWrapper.eq(Ruins::getYearsId, yearsId);
        });

        // 类别检索
        Optional.ofNullable(ruins.getCategoryId()).ifPresent(categoryId -> {
            queryWrapper.eq(Ruins::getCategoryId, categoryId);
        });

        queryWrapper.orderByDesc(Ruins::getStatus, Ruins::getUpdateDate);

        Page<Ruins> ruinsPage = super.page(page, queryWrapper);

        List<Ruins> records = ruinsPage.getRecords();
        records.forEach(item -> {
            // 设置创建人
            String userId = item.getCreateBy();
            item.setCreateBy(userService.getNameById(userId));
        });
        return ruinsPage;
    }



    /**
     * 普通用户拥有查看权限
     * @param id
     * @return
     */
    @Override
    public Ruins getById(Serializable id) {
        Ruins ruins;
        if (SubjectUtils.isAdmin()) {
            ruins = super.getById(id);
        } else {
            ruins = getOne(Wrappers.<Ruins>lambdaQuery().eq(Ruins::getId, id).eq(Ruins::getCreateBy, SubjectUtils.getCurrentUser().getId()));
        }
        return ruins;
    }

    /**
     * remove by id
     * @param id ruins id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        Boolean removed;
        if (SubjectUtils.isAdmin()) {
            removed = super.removeById(id);
        } else {
            removed = super.remove(Wrappers.<Ruins>lambdaQuery().eq(Ruins::getId, id).eq(Ruins::getCreateBy, SubjectUtils.getCurrentUser().getId()));
        }
        return removed;
    }

    /**
     * 审核
     * @param id 审核id
     * @param status 审核状态
     */
    @Override
    public void setStatus(String id, Integer status) {
        Ruins ruins = getById(id);
        Assert.notNull(ruins, "数据为空值");
        ruins.setStatus(status);
        updateById(ruins);
    }

    /**
     * 查看待审核ruins
     * @param page 参数
     * @param ruins 实体
     * @return ruins 列表
     */
    @Override
    public IPage reviewSearch(Page<Ruins> page, Ruins ruins) {

        QueryWrapper<Ruins> queryWrapper = new QueryWrapper<>();

        // 按名称查询
        if (!StringUtils.isEmpty(ruins.getTitle())) {
            queryWrapper.like("title", ruins.getTitle());
        }

        queryWrapper.eq("status", 2).or().eq("status", 3);

        Page<Ruins> ruinsPage = super.page(page, queryWrapper);

        return ruinsPage;
    }
}
