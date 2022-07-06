package com.msm.admin.greatWall.gwRoam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.greatWall.gwRoam.entity.GwRoam;
import com.msm.admin.greatWall.gwRoam.mapper.RoamMapper;
import com.msm.admin.greatWall.gwRoam.service.RoamService;
import com.msm.admin.modules.info.entity.Celebrity;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.taskdefs.Sleep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxy
 * @date 2022/2/18 10:09
 */
@Service
public class RoamServiceImpl extends ServiceImpl<RoamMapper, GwRoam> implements RoamService {

    @Autowired
    private RoamMapper roamMapper;


    @Override
    public Page pageSearch(Page<GwRoam> page, GwRoam roam) {

        QueryWrapper<GwRoam> wrapper=new QueryWrapper<>();
        if (roam !=null){
            if (StringUtils.isNotEmpty(roam.getDepartId())){
                wrapper.eq("depart_id",roam.getDepartId());
            }
        }
        wrapper.orderByDesc("create_date");
        Page<GwRoam> gwRoamPage = roamMapper.selectPage(page, wrapper);
        return gwRoamPage;
    }


}
