package com.msm.admin.modules.pub.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.system.entity.SysDictType;
import com.msm.admin.modules.system.entity.SysDictValue;
import com.msm.admin.modules.system.service.SysDictTypeService;
import com.msm.admin.modules.system.service.SysDictValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author quavario@gmail.com
 * @date 2019/12/18 17:15
 */
@Service
public class PubDictValueService {
    @Autowired
    private SysDictTypeService dictTypeService;

    @Autowired
    private SysDictValueService dictValueService;
    

    private static final String[] QUERY_LIST_FIELD = {
            "label", "value", "sort",
    };


    public List<SysDictValue> getByTypeId(String dictTypeId) {
        QueryWrapper<SysDictValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(QUERY_LIST_FIELD).eq("dict_type_id", dictTypeId).eq("status", 1);
        return dictValueService.list(queryWrapper);
    }
}
