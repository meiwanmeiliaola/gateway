package com.msm.admin.greatWall.gwRelic.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.common.enums.ErrorInfo;
import com.msm.admin.config.FileUploadProperties;
import com.msm.admin.framework.annotation.View;
import com.msm.admin.greatWall.common.CommonInter;
import com.msm.admin.greatWall.gwCollection.entity.CollectionEntity;
import com.msm.admin.greatWall.gwCollection.service.CollectionService;
import com.msm.admin.greatWall.gwRelic.entity.GwRelic;

import com.msm.admin.greatWall.gwRelic.entity.Pql;
import com.msm.admin.greatWall.gwRelic.enums.CateNameEnum;
import com.msm.admin.greatWall.gwRelic.service.GwRelicService;
import com.msm.admin.greatWall.gwRelic.service.impl.GwRelicServiceImpl;
import com.msm.admin.modules.common.entity.Years;
import com.msm.admin.modules.common.mapper.YearsMapper;
import com.msm.admin.modules.relic.mapper.RelicMapper;
import com.msm.admin.modules.statistics.mapper.RelicStatisticsMapper;
import com.msm.admin.modules.system.entity.SysUser;
import com.msm.admin.modules.system.service.SysDepartService;
import com.msm.admin.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;

/**
 * @author zxy
 * @date 2022/2/9 15:48
 * 长城文物(二维,三维文物)
 */
@RestController
@RequestMapping(value = "/gwRelic")
public class GwRelicController {

    @Autowired
    private GwRelicService relicService;

    @Autowired
    private SysDepartService departService;

    @Autowired
    private GwRelicServiceImpl relicServiceImpl;

    @Autowired
    private CommonInter commonInter;

    @Autowired
    private YearsMapper yearsMapper;

    @Autowired
    private RelicMapper relicMapper;

    @Autowired
    private RelicStatisticsMapper statisticsMapper;

    @Autowired
    private CollectionService collectionService;


    @Autowired
    private FileUploadProperties fileUploadProperties;



    /**
    * 添加/编辑 二维(三维)数据
    */
    @PutMapping
    public GwRelic save(@RequestBody GwRelic relic){
        QueryWrapper<GwRelic> wrapper=new QueryWrapper<>();

        //判断部门是否存在
        SysUser user = SubjectUtils.getCurrentUser();
        if (StringUtils.isEmpty(relic.getDepId())){
            relic.setDepId(user.getDepId());
            wrapper.eq("dep_id",user.getDepId());
        }else {
            wrapper.eq("dep_id",relic.getDepId());
        }
        List<Object> list;

        wrapper.eq("name",relic.getName());
        wrapper.eq("diff_status",relic.getDiffStatus());
         if(StringUtils.isNotEmpty(relic.getCreateDate())){
             relic.setUpdateDate(DateUtil.getNowTime());
         }else {
            //获取当前时间
            relic.setCreateDate(DateUtil.getNowTime());
        }

        if (StringUtils.isNotEmpty(relic.getId())){
            wrapper.ne("id",relic.getId());
            list= relicService.listObjs(wrapper);
        }else {
            list=relicService.listObjs(wrapper);
        }
        boolean b = commonInter.existName(relic, wrapper);

        System.out.println("是否存在:"+ b);

        boolean flag = list.size() ==0;
        if (flag){
            relicService.saveOrUpdate(relic);
        }else {
            ExceptionThrower.of(ErrorInfo.TITLE_EXIST);
        }
         return relic;
    }

    /**
     * 根据id删除
     */
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id){
        relicService.removeById(id);
    }

    /**
     *根据id查询
     */
    @GetMapping(value = "/{id}")
    @View(type = "relic")
    public GwRelic getById(@PathVariable String id){
        GwRelic relic = relicService.getById(id);
        String depName = departService.getNameById(relic.getDepId());
        relic.setDepName(depName);
        String diffStatusName=relic.getDiffStatus() == 1 ? "二维" : "三维";
        relic.setDiffStatusName(diffStatusName);

        String pqlName=relic.getDiffStatus() == 1 ? "精美" : "普通";
        relic.setPqlName(pqlName);

        //年代
        if (StringUtils.isNoneEmpty(relic.getYearsId())){
            Years years=yearsMapper.selectById(relic.getYearsId());
            if (years !=null){
                relic.setYearName(years.getName());
            }
        }
        //材质
        String type="material";
        if (StringUtils.isNotEmpty(relic.getMaterialId())){
            String cateName = statisticsMapper.getByMaterialId(relic.getMaterialId(),type);
            relic.setMaterialName(cateName);
        }

        String cateName = CateNameEnum.getName(relic.getCategoryId());
        relic.setCategoryName(cateName);

        return relic;
    }

    @GetMapping(value = "/list")
    public Page page(Page<GwRelic> page,GwRelic relic){
        //根据工艺评定等级排列
        return relicService.pageSearch(page,relic);
    }

    @GetMapping(value = "/materialList")
    public List<Map<String,String>> mapList(){
        return PqlUtil.list();
    }

    @GetMapping(value = "/review/{id}/{status}")
    public void status(@PathVariable String id,@PathVariable Integer status){
        relicService.setStatus(id,status);
    }


    @GetMapping(value = "recommend")
    public Page<GwRelic> recommendList(Page<GwRelic> page,GwRelic relic){
        return relicService.recommonList(page,relic);
    }


    //三维根据名称查询二维详情
    @GetMapping(value = "/detail")
    public GwRelic detail(GwRelic gwRelic){
        QueryWrapper<GwRelic> wrapper=new QueryWrapper<>();
        if (gwRelic !=null){
            if (StringUtils.isNotEmpty(gwRelic.getName())){
                wrapper.eq("name",gwRelic.getName());
            }
            if(StringUtils.isNotEmpty(gwRelic.getId())){
                wrapper.eq("id",gwRelic.getId());
            }
            Integer diffStatus=gwRelic.getDiffStatus() ==null ? 1 : gwRelic.getDiffStatus();
            wrapper.eq("diff_status",diffStatus);
        }
        GwRelic relic = relicService.getOne(wrapper);
        if (relic !=null){
            String depName = departService.getNameById(relic.getDepId());
            relic.setDepName(depName);
        }
        String diffStatusName=relic.getDiffStatus() == 1 ? "二维" : "三维";
        relic.setDiffStatusName(diffStatusName);

        String pqlName=relic.getDiffStatus() == 1 ? "精美" : "普通";
        relic.setPqlName(pqlName);

        //判断压缩包是否存在
        if (StringUtils.isNotEmpty(relic.getZipUrl())){
            String filePath=relic.getZipUrl();
            filePath=fileUploadProperties.getMappingPath()+"/msm-admin/"+filePath.replace("/data/","").replace("index.html","marmoset.js");
            System.out.println("响应的数据:"+filePath);
            File file=new File(filePath);

            boolean exists = file.exists();
            if (exists){
                relic.setExistsZipUrl(1);
            }else {
                relic.setExistsZipUrl(2);
            }
        }

        //年代
        if (StringUtils.isNoneEmpty(relic.getYearsId())){
            Years years=yearsMapper.selectById(relic.getYearsId());
            if (years !=null){
                relic.setYearName(years.getName());
            }
        }
        //材质
        String type="material";
        if (StringUtils.isNotEmpty(relic.getMaterialId())){
            String cateName = statisticsMapper.getByMaterialId(relic.getMaterialId(),type);
            relic.setMaterialName(cateName);
        }

        String cateName = CateNameEnum.getName(relic.getCategoryId());
        relic.setCategoryName(cateName);

        //馆藏单位名称
        if (StringUtils.isNotEmpty(relic.getTypeId())){
            CollectionEntity entity = collectionService.getById(relic.getTypeId());
            if (entity !=null) {
                relic.setCollectionName(entity.getName());
            }
        }
        return relic;

    }

    //精美 & 普通
    @GetMapping(value = "pqlList")
    public List<Pql> list(){
        return Pql.pqlList();
    }


    @GetMapping(value = "demo")
    public void demo(GwRelic gwRelic){
      QueryWrapper<GwRelic> wrapper = new QueryWrapper<>();
      wrapper.eq("category_id",gwRelic.getCategoryId());

      relicMapper.updateByCateId(gwRelic);
    }



}
