package com.msm.admin.greatWall.gwCollection.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.greatWall.gwCollection.entity.CollectionEntity;
import com.msm.admin.greatWall.gwCollection.mapper.CollectionMapper;

import java.util.List;
import java.util.Map;

public interface CollectionService extends IService<CollectionEntity> {
    Page<CollectionEntity> pageSearch(Page<CollectionEntity> page, CollectionEntity collectionEntity);



}
