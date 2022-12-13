package com.msm.admin.greatWall.gwPoetry.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.Log;
import com.msm.admin.greatWall.gwPoetry.entity.PoetryEntity;
import com.msm.admin.greatWall.gwPoetry.service.PoetryService;
import com.msm.admin.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("poetry")
public class PoetryController {


    @Resource
    private PoetryService poetryService;


    @PutMapping
    @Log("编辑长城诗歌")
    @RequiresPermissions("info:poetry:edit")
    public PoetryEntity save(@RequestBody PoetryEntity poetryEntity){

        if (poetryEntity.getId() ==null){
            poetryEntity.setStatus(0);
        }

        if(StringUtils.isNotEmpty(poetryEntity.getCreateDate())){
            poetryEntity.setUpdateDate(DateUtil.getNowTime());
        }else {
            //获取当前时间
            poetryEntity.setCreateDate(DateUtil.getNowTime());
        }
        poetryService.saveOrUpdate(poetryEntity);

        return poetryEntity;

    }

    @DeleteMapping(value = "/{id}")
    @Log("删除长城诗歌")
    @RequiresPermissions("info:poetry:delete")
    public void delete(@PathVariable String id){
        poetryService.removeById(id);
    }

    @GetMapping(value = "/{id}")
    public PoetryEntity getById(@PathVariable String id){
        return poetryService.getById(id);
    }


    @GetMapping("list")
    @RequiresPermissions("info:poetry:list")
    public IPage list(Page<PoetryEntity> page,PoetryEntity poetryEntity){
        return poetryService.pageSearch(page,poetryEntity);
    }

    @GetMapping("query")
    public List<PoetryEntity> query(){
        QueryWrapper<PoetryEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("status",1);
        wrapper.orderByDesc("create_date");
        return poetryService.list(wrapper);
    }

    @GetMapping("review/{id}/{status}")
    public void review(@PathVariable String id,@PathVariable Integer status){
        PoetryEntity poetryEntity=new PoetryEntity();
        poetryEntity.setStatus(status);
        poetryEntity.setId(id);
        poetryService.updateById(poetryEntity);
    }




}
