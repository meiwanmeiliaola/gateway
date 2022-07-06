package com.msm.admin.greatWall.gwCollection.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.common.enums.ErrorInfo;
import com.msm.admin.greatWall.gwCollection.entity.CollectionEntity;
import com.msm.admin.greatWall.gwCollection.service.CollectionService;
import com.msm.admin.utils.ExceptionThrower;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/3/15 14:59
 */

@RestController
@RequestMapping(value = "collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @PutMapping
    public void save(@RequestBody CollectionEntity collectionEntity){
        //判断是否重复
        QueryWrapper<CollectionEntity> wrapper=new QueryWrapper<>();
        if (collectionEntity !=null){
            if (StringUtils.isNotEmpty(collectionEntity.getId())){
                wrapper.ne("id",collectionEntity.getId());
            }
            if (StringUtils.isNotEmpty(collectionEntity.getType())){
                wrapper.eq("type",collectionEntity.getType());
            }
            if (StringUtils.isNotEmpty(collectionEntity.getAreaId())){
                wrapper.eq("area_id",collectionEntity.getAreaId());
            }
            if (StringUtils.isNotEmpty(collectionEntity.getName())){
                wrapper.eq("name",collectionEntity.getName());
            }
        }
        int count = collectionService.count(wrapper);
        if (count>0){
            ExceptionThrower.of(ErrorInfo.TITLE_EXIST);
        }
        collectionService.saveOrUpdate(collectionEntity);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable String id){
        collectionService.removeById(id);
    }

    @GetMapping(value = "{id}")
    public CollectionEntity getById(@PathVariable String id){
        return collectionService.getById(id);
    }

    @GetMapping(value = "list")
    public List<CollectionEntity> list(CollectionEntity collectionEntity){
         QueryWrapper<CollectionEntity> wrapper=new QueryWrapper<>();
         String type= StringUtils.isEmpty(collectionEntity.getType()) ? "1":collectionEntity.getType();
         wrapper.eq("type",type);
         return collectionService.list(wrapper);
    }

    @GetMapping(value = "collectionList")
    public List<Map<String,String>> collectionList(CollectionEntity collectionEntity){
        QueryWrapper<CollectionEntity> wrapper=new QueryWrapper<>();
        String type= StringUtils.isEmpty(collectionEntity.getType()) ? "1":collectionEntity.getType();
        wrapper.eq("type",type);
        List<CollectionEntity> entityList = collectionService.list(wrapper);
        List<Map<String,String>> list=new LinkedList<>();
        for (int i = 0; i < entityList.size(); i++) {
            Map<String,String> map=new HashMap<>();
            Integer num =i+1;
            map.put("label",entityList.get(i).getName());
            map.put("value",entityList.get(i).getId());
            map.put("sort",num.toString());
            map.put("type","Collection");
            list.add(map);
        }
        return list;
    }

    @GetMapping(value = "pageList")
    public Page<CollectionEntity> page(Page<CollectionEntity> page,CollectionEntity collectionEntity){
        return collectionService.pageSearch(page,collectionEntity);
    }



}
