package com.msm.admin.modules.relic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.msm.admin.modules.common.service.impl.RedisService;
import com.msm.admin.modules.relic.entity.RelicCategory;
import com.msm.admin.modules.relic.mapper.RelicCategoryMapper;
import com.msm.admin.modules.relic.service.RelicCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: area
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
@Cacheable("relicCategory")
public class RelicCategoryServiceImpl extends ServiceImpl<RelicCategoryMapper, RelicCategory> implements RelicCategoryService {

    @Autowired
    private RelicCategoryMapper relicCategoryMapper;

//    @Autowired
//    private RedisService redisService;

    @Override
    public List<RelicCategory> getListByParentId(String parentId) {
        return relicCategoryMapper.selectListByParentId(parentId);
    }

    @Override
    public List<RelicCategory> list() {
        List<RelicCategory> list = super.list();
        return list;
    }
}
