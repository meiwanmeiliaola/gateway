package com.msm.admin.greatWall.gwCollection.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.greatWall.gwCollection.entity.CollectionEntity;
import com.msm.admin.greatWall.gwCollection.mapper.CollectionMapper;
import com.msm.admin.greatWall.gwCollection.service.CollectionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/3/15 15:01
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, CollectionEntity> implements CollectionService {


    @Autowired
    private CollectionMapper collectionMapper;


    @Override
    public Page<CollectionEntity> pageSearch(Page<CollectionEntity> page, CollectionEntity collectionEntity) {
        QueryWrapper<CollectionEntity> wrapper=new QueryWrapper<>();
        if (collectionEntity !=null){
            if (StringUtils.isNotEmpty(collectionEntity.getName())){
                wrapper.like("name",collectionEntity.getName());
            }

            if (StringUtils.isNotEmpty(collectionEntity.getType())){
                wrapper.like("type",collectionEntity.getType());
            }
        }
        wrapper.orderByDesc("create_date");

        return collectionMapper.selectPage(page,wrapper);
    }


}
