package com.msm.admin.greatWall.gwRelic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.greatWall.gwCollection.entity.CollectionEntity;
import com.msm.admin.greatWall.gwCollection.service.CollectionService;
import com.msm.admin.greatWall.gwRelic.entity.GwRelic;


import com.msm.admin.greatWall.gwRelic.enums.CateNameEnum;
import com.msm.admin.greatWall.gwRelic.mapper.GwRelicServiceMapper;
import com.msm.admin.greatWall.gwRelic.service.GwRelicService;
import com.msm.admin.modules.common.entity.Years;
import com.msm.admin.modules.common.mapper.YearsMapper;
import com.msm.admin.modules.relic.service.RelicCategoryService;
import com.msm.admin.modules.system.service.SysDepartService;
import com.msm.admin.utils.SubjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxy
 * @date 2022/2/9 16:38
 */
@Service
public class GwRelicServiceImpl extends ServiceImpl<GwRelicServiceMapper, GwRelic> implements GwRelicService {

    @Autowired
    private GwRelicServiceMapper relicServiceMapper;

    @Autowired
    private RelicCategoryService categoryService;

    @Autowired
    private YearsMapper yearsMapper;

    @Autowired
    private SysDepartService departService;

    @Autowired
    private CollectionService collectionService;

    @Override
    public Page pageSearch(Page<GwRelic> page, GwRelic relic) {

        QueryWrapper<GwRelic> queryWrapper = new QueryWrapper<>();

        queryWrapper=SubjectUtils.isAdmin(queryWrapper);
        if (SubjectUtils.isAdminNull()){
            queryWrapper.orderByAsc("pql");
        }else {
            queryWrapper.orderByAsc("status");
        }


        if (relic !=null){
            //按照文物名称查询
            if (StringUtils.isNotEmpty(relic.getName())){
                queryWrapper.like("name",relic.getName());
            }

            //按照年代查询
            if (StringUtils.isNotEmpty(relic.getYearsId())){
                queryWrapper.eq("years_id",relic.getYearsId());
            }
            //按照馆藏单位查询
            if(StringUtils.isNotEmpty(relic.getTypeId())){
                queryWrapper.eq("type_id",relic.getTypeId());
            }
            //按照景点id
            if(StringUtils.isNotEmpty(relic.getDepId())){
                queryWrapper.eq("dep_id",relic.getDepId());
            }
            //按照材质查询
            if (StringUtils.isNotEmpty(relic.getMaterialId())){
                queryWrapper.eq("mat_id",relic.getMaterialId());
            }
            if (relic.getDiffStatus() !=null){
                queryWrapper.eq("diff_status",relic.getDiffStatus());
            }
            if (StringUtils.isNotEmpty(relic.getCategoryId())){
                queryWrapper.eq("category_id",relic.getCategoryId());
            }
            //根据审核状态查询
            if (relic.getStatus() !=null){
                if (relic.getStatus() ==20){
                    queryWrapper.eq("status",0).or().eq("status",2);
                }else {
                    queryWrapper.eq("status",relic.getStatus());
                }
            }
        }

        Page<GwRelic> gwRelicPage = relicServiceMapper.selectPage(page, queryWrapper);
        List<GwRelic> relicList = gwRelicPage.getRecords();
        relicList.forEach(item ->{

            if (item.getDiffStatus() !=null){
                String diffStatusName=item.getDiffStatus() == 1 ? "二维" : "三维";
                item.setDiffStatusName(diffStatusName);
            }

            //馆藏单位名称
            if (StringUtils.isNotEmpty(item.getTypeId())){
                CollectionEntity entity = (CollectionEntity) collectionService.getById(item.getTypeId());
                if (entity !=null) {
                    item.setCollectionName(entity.getName());
                }
            }

            // 设置部门名称
            String depName = departService.getNameById(item.getDepId());
            item.setDepName(depName);

            //年代
            Years years=yearsMapper.selectById(item.getYearsId());
            if (years !=null){
                item.setYearName(years.getName());
            }

            //设置类别
/*            RelicCategory category = categoryService.getById(item.getCategoryId());
            if (category !=null && !StringUtils.isEmpty(category.getName())){
                item.setCategoryName(category.getName());
            }*/
            String cateName = CateNameEnum.getName(item.getCategoryId());
            item.setCategoryName(cateName);
        });

        return gwRelicPage;
    }

    @Override
    public void setStatus(String id, Integer status) {
        GwRelic relic =relicServiceMapper.selectById(id);
        if (relic !=null){
            relic.setStatus(status);
            updateById(relic);
        }
    }

    @Override
    public Page<GwRelic> recommonList(Page<GwRelic> page,GwRelic relic) {
        QueryWrapper<GwRelic> wrapper=new QueryWrapper<>();
        if (relic !=null){
            if (StringUtils.isNoneEmpty(relic.getId())){
                wrapper.ne("id",relic.getId());
            }
            if (StringUtils.isNotEmpty(relic.getYearsId())){
                wrapper.eq("years_id",relic.getYearsId());
            }
            if (StringUtils.isNotEmpty(relic.getMaterialId())){
                wrapper.eq("mat_id",relic.getMaterialId());
            }
            if (StringUtils.isNotEmpty(relic.getDepId())){
             wrapper.eq("dep_id",relic.getDepId());
            }
            if (StringUtils.isNotEmpty(relic.getCategoryId())){
                wrapper.eq("category_id",relic.getCategoryId());
            }
            if (StringUtils.isNotEmpty(relic.getTypeId())){
                wrapper.eq("type_id",relic.getTypeId());

            }
            if (relic.getDiffStatus() !=null){
                wrapper.eq("diff_status",relic.getDiffStatus());
            }

        }
        wrapper.eq("status",1);
        wrapper.orderByAsc("pql");
        page= relicServiceMapper.selectPage(page,wrapper);
        return page;
    }



}
