package com.msm.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.system.entity.SysDictValue;
import com.msm.admin.modules.system.mapper.SysDictValueMapper;
import com.msm.admin.modules.system.service.SysDictValueService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Description: area
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
public class SysDictValueServiceImpl extends ServiceImpl<SysDictValueMapper, SysDictValue> implements SysDictValueService {
    @Override
    public IPage pageSearch(Page<SysDictValue> page, SysDictValue dictValue) {
        QueryWrapper<SysDictValue> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(dictValue.getDictTypeId())) {
            queryWrapper.eq("dict_type_id", dictValue.getDictTypeId());
        }

        if (!StringUtils.isEmpty(dictValue.getLabel())) {
            queryWrapper.like("label", dictValue.getLabel().trim());
        }

        queryWrapper.orderByDesc("update_date", "create_date");

        Page<SysDictValue> valuePage = page(page, queryWrapper);
        return valuePage;
    }
}
