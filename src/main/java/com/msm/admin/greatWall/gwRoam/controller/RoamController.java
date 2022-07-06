package com.msm.admin.greatWall.gwRoam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.greatWall.gwRoam.entity.GwRoam;
import com.msm.admin.greatWall.gwRoam.service.RoamService;
import com.msm.admin.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zxy
 * @date 2022/2/18 9:55
 * 全景漫游
 */
@RestController
@RequestMapping(value = "gwRoam")
public class RoamController {


    @Autowired
    private RoamService roamService;

    @PutMapping
    public GwRoam save(@RequestBody GwRoam gwRoam){
        if(StringUtils.isNotEmpty(gwRoam.getCreateDate())){
            gwRoam.setUpdateDate(DateUtil.getNowTime());
        }else {
            //获取当前时间
            gwRoam.setCreateDate(DateUtil.getNowTime());
        }
        if (gwRoam.getStatus() ==null){
            gwRoam.setStatus(0);
        }
        roamService.saveOrUpdate(gwRoam);
        return gwRoam;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id){
        roamService.removeById(id);
    }

    @GetMapping("/{id}")
    public GwRoam getById(@PathVariable String id){
        GwRoam gwRoam = roamService.getById(id);
        return gwRoam;
    }

    @GetMapping("/list")
    public Page page(Page<GwRoam> page,GwRoam roam){
        return roamService.pageSearch(page,roam);
    }


}
