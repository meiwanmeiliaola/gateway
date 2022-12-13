package com.msm.admin.greatWall.gwArmchair.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.greatWall.gwArmchair.entity.Armchair;
import com.msm.admin.greatWall.gwArmchair.mapper.ArmchairMapper;
import com.msm.admin.greatWall.gwArmchair.service.ArmchairService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArmchairServiceImpl extends ServiceImpl<ArmchairMapper, Armchair> implements ArmchairService {

    @Resource
    private ArmchairMapper armchairMapper;


    @Override
    public IPage<Armchair> pageSearch(Page<Armchair> page, Armchair armchair) {

        QueryWrapper<Armchair> wrapper=new QueryWrapper<>();
        if (armchair.getStatus() !=null && armchair.getStatus()==20){
            wrapper.eq("status",0).or().eq("status",2);
        }
        wrapper.orderByAsc("status");
        return armchairMapper.selectPage(page,wrapper);
    }
}
