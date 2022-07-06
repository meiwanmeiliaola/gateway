package com.msm.admin.modules.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.common.entity.Years;
import com.msm.admin.modules.common.service.YearsService;
import com.msm.admin.modules.info.entity.Celebrity;
import com.msm.admin.modules.info.mapper.CelebrityMapper;
import com.msm.admin.modules.info.service.CelebrityService;
import com.msm.admin.modules.system.service.SysUserService;
import com.msm.admin.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description: Celebrity
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
public class CelebrityServiceImpl extends ServiceImpl<CelebrityMapper, Celebrity> implements CelebrityService {

    @Autowired
    private YearsService yearsService;
    @Autowired
    private SysUserService userService;

    @Override
    public Celebrity getById(Serializable id) {
        Celebrity celebrity;
        if (!SubjectUtils.isAdmin()) {
            // 防止用户更改非自己发布的内容
            celebrity = getOne(Wrappers.<Celebrity>lambdaQuery().eq(Celebrity::getId, id).eq(Celebrity::getCreateBy, SubjectUtils.getCurrentUser().getId()));
        } else {
            celebrity = super.getById(id);
        }
        return celebrity;
    }

    @Override
    @CacheEvict(value = {"pubCelebrity"}, key = "'getById:' + #entity.id", condition = "#entity.id != null ")
    public boolean saveOrUpdate(Celebrity entity) {
        if (!SubjectUtils.isAdmin()) {
            if (entity.getId() == null) {
                entity.setStatus(3);
            } else {
                Celebrity celebrity = getById(entity.getId());
                if (celebrity.getStatus() != 1) {
                    entity.setStatus(3);
                }
            }
        }
        return super.saveOrUpdate(entity);
    }

    @Override
    @CacheEvict(value = {"pubCelebrity"}, key = "'getById:' + #id")
    public boolean removeById(Serializable id) {
        Boolean removed;
        if (SubjectUtils.isAdmin()) {
            removed = super.removeById(id);
        } else {
            removed = remove(Wrappers.<Celebrity>lambdaQuery().eq(Celebrity::getId, id).eq(Celebrity::getCreateBy, SubjectUtils.getCurrentUser().getId()));
        }
        return removed;
    }


    @Override
    public List<Celebrity> list(Wrapper<Celebrity> queryWrapper) {
        List<Celebrity> list = super.list(queryWrapper);
        return list;
    }

    @Override
    public IPage pageSearch(Page<Celebrity> page, Celebrity celebrity) {
        QueryWrapper<Celebrity> queryWrapper = new QueryWrapper<>();
        if (!SubjectUtils.isAdmin()) {
//            queryWrapper.eq("create_by", SubjectUtils.getCurrentUser().getId());
            queryWrapper.eq("dep_id", SubjectUtils.getCurrentUser().getDepId());
        }

        if (celebrity != null) {
            // 按文物年代查询
            if (!StringUtils.isEmpty(celebrity.getYearsId())) {
                String yearsId = celebrity.getYearsId();
                Years years = yearsService.getById(yearsId);

                Assert.notNull(years);

                List<String> yearsIds = yearsService.list(
                        Wrappers.<Years>lambdaQuery()
                                .select(Years::getId)
                                .eq(Years::getParentId, yearsId)
                ).stream().map(Years::getId).collect(Collectors.toList());

                queryWrapper.in("years_id", yearsIds);

            }

            if (!StringUtils.isEmpty(celebrity.getName())) {
                queryWrapper.like("name", celebrity.getName());
            }
        }

        queryWrapper.orderByDesc("update_date", "create_date");
        Page<Celebrity> celebrityPage = super.page(page, queryWrapper);
        List<Celebrity> records = celebrityPage.getRecords();

        // 设置年代名称
        records.forEach(item -> {
            Years years = yearsService.getById(item.getYearsId());
            if (years != null && !StringUtils.isEmpty(years.getName())) {
                item.setYearsName(years.getName());
            }
            // 设置创建人
            String userId = item.getCreateBy();
            item.setCreateBy(userService.getNameById(userId));

        });

        return celebrityPage;
    }



    @Override
    public IPage<Celebrity> reviewSearch(Page<Celebrity> page, Celebrity celebrity) {
        QueryWrapper<Celebrity> queryWrapper = new QueryWrapper<>();

        if (!SubjectUtils.isAdmin()) {
            queryWrapper.eq("create_by", SubjectUtils.getCurrentUser().getId());
        }

        if (!StringUtils.isEmpty(celebrity.getName())) {
            queryWrapper.like("name", celebrity.getName());
        }

        queryWrapper.eq("status", 2).or().eq("status", 3);


        queryWrapper.orderByAsc("status", "update_by");
        Page<Celebrity> celebrityPage = super.page(page, queryWrapper);

        return celebrityPage;
    }

    /**
     * TODO 更新权限校验
     * @param id
     * @param status
     */
    @Override
    @CacheEvict(value = {"pubCelebrity"}, key = "'getById:' + #id", condition = "#id != null")
    public void setStatus(String id, Integer status) {
        Celebrity celebrity = getById(id);
        Assert.notNull(celebrity, "数据为空");
        celebrity.setStatus(status);
        updateById(celebrity);
    }

    @Override
    public String check(Celebrity celebrity) {
        String result = null;

        LambdaQueryWrapper<Celebrity> queryWrapper = Wrappers.lambdaQuery();

        // 编辑用户时校验规则排除自身
        if (!isNewCelebrity(celebrity)) {
            queryWrapper.ne(Celebrity::getId, celebrity.getId());
        }


        // 校验姓名
        if (!StringUtils.isEmpty(celebrity.getName())) {
            List<Celebrity> list = list(queryWrapper
                    .eq(Celebrity::getName, celebrity.getName()).select(Celebrity::getName));
            return list.size() == 0 ? null : "该姓名已经存在，请检查姓名";
        }

        return result;
    }

    Boolean isNewCelebrity(Celebrity celebrity) {
        return celebrity.getId() == null || "".equals(celebrity.getId());
    }
}
