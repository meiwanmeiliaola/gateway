package com.msm.admin.greatWall.gwPoetry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.greatWall.gwNum.entity.GwNum;
import com.msm.admin.greatWall.gwPoetry.entity.PoetryEntity;
import com.msm.admin.greatWall.gwPoetry.mapper.PoetryMapper;
import com.msm.admin.greatWall.gwPoetry.service.PoetryService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class PoetryServiceImpl extends ServiceImpl<PoetryMapper, PoetryEntity> implements PoetryService {


    @Resource
    private PoetryMapper poetryMapper;

    @Override
    public IPage pageSearch(Page<PoetryEntity> page, PoetryEntity poetryEntity) {
        QueryWrapper<PoetryEntity> wrapper=new QueryWrapper<>();

        Optional.ofNullable(poetryEntity)
                .ifPresent(t ->{
                    if (ObjectUtils.isNotEmpty(t.getSource())){
                        wrapper.like("source",t.getSource());
                    }
                    if (ObjectUtils.isNotEmpty(t.getStatus())){
                        if (t.getStatus()==20){
                            wrapper.eq("status",0).or().eq("status",2);
                        }else {
                            wrapper.eq("status",t.getStatus());
                        }

                    }

                });
        wrapper.orderByAsc("status");

        return poetryMapper.selectPage(page,wrapper);

    }
}
