package com.msm.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.common.entity.TreeData;
import com.msm.admin.modules.system.entity.SysDictType;
import com.msm.admin.modules.system.mapper.SysDictTypeMapper;
import com.msm.admin.modules.system.service.SysDictTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Description: area
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {
    @Override
    public IPage pageSearch(Page<SysDictType> page, SysDictType dictType) {
        QueryWrapper<SysDictType> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(dictType.getName())) {
            queryWrapper.eq("name", dictType.getName());
        }

        queryWrapper.orderByDesc("update_date", "create_date");
        Page<SysDictType> page1 = page(page, queryWrapper);
        return page1;
    }
}
