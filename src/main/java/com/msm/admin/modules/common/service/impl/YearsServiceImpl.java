package com.msm.admin.modules.common.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.common.entity.Years;
import com.msm.admin.modules.common.mapper.YearsMapper;
import com.msm.admin.modules.common.service.YearsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @Description: area
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
public class YearsServiceImpl extends ServiceImpl<YearsMapper, Years> implements YearsService {

    @Autowired
    private YearsMapper yearsMapper;

    private static final String[] QUERY_LIST_FIELD = {
            "id", "name", "image", "parent_id", "sort", "type"
    };

    @Override
    public List<Years> getListByParentId(String parentId) {
        List<Years> list = super.list(
                Wrappers.<Years>lambdaQuery()
                        .eq(Years::getParentId, parentId)
        );
        return list;
    }

    @Override
    public boolean saveOrUpdate(Years entity) {

        if (entity.getSort() == null) {
            entity.setSort(999);
        }

        // 设置类型
        entity.setType(entity.getParentId() == null ? 1 : 2);
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public List<Years> list() {
        return super.list();
    }
}
