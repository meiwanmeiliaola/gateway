package com.msm.admin.greatWall.gwCulture.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.common.enums.ErrorInfo;
import com.msm.admin.greatWall.gwCulture.entity.CultureCategory;
import com.msm.admin.greatWall.gwCulture.entity.GwCulture;
import com.msm.admin.greatWall.gwCulture.enums.CategoryNameEnum;
import com.msm.admin.greatWall.gwCulture.service.CultureService;
import com.msm.admin.greatWall.gwNews.entity.GwNews;
import com.msm.admin.greatWall.gwRelic.entity.Pql;
import com.msm.admin.modules.system.entity.SysDictType;
import com.msm.admin.modules.system.entity.SysDictValue;
import com.msm.admin.modules.system.entity.SysUser;
import com.msm.admin.modules.system.service.SysDictValueService;
import com.msm.admin.utils.DateUtil;
import com.msm.admin.utils.ExceptionThrower;
import com.msm.admin.utils.SubjectUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zxy
 * @date 2022/2/11 17:23
 * 长城文化
 */
@RestController
@RequestMapping(value = "/culture")
public class CultureController {


    @Autowired
    private CultureService cultureService;
    @Autowired
    private SysDictValueService valueService;

    @PutMapping
    public GwCulture save(@RequestBody GwCulture culture){
        QueryWrapper<GwCulture> wrapper=new QueryWrapper<>();

        //判断部门是否存在
        SysUser user = SubjectUtils.getCurrentUser();
        if (StringUtils.isEmpty(culture.getDepId())){
            culture.setDepId(user.getDepId());
            wrapper.eq("dep_id",user.getDepId());
        }else {
            wrapper.eq("dep_id",culture.getDepId());
        }
        List<Object> list;
        wrapper.eq("title",culture.getTitle());
        if (StringUtils.isEmpty(culture.getCreateDate())){
            culture.setCreateDate(DateUtil.getNowTime());
        }else {
            culture.setUpdateDate(DateUtil.getNowTime());
        }
        if (culture.getStatus() ==null){
            culture.setStatus(0);
        }

        if (StringUtils.isNotEmpty(culture.getId())){
            wrapper.ne("id",culture.getId());
            list= cultureService.listObjs(wrapper);
        }else {
            list=cultureService.listObjs(wrapper);
        }

        boolean flag = list.size() ==0;
        if (flag){
            cultureService.saveOrUpdate(culture);
        }else {
            ExceptionThrower.of(ErrorInfo.TITLE_EXIST);
        }

        cultureService.saveOrUpdate(culture);

        return culture;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id){
        cultureService.removeById(id);
    }

    @GetMapping(value = "/{id}")
    public GwCulture getById(@PathVariable String id){
        GwCulture gwCulture = cultureService.getById(id);
        if (gwCulture !=null){
            if (StringUtils.isNotEmpty(gwCulture.getCategoryId())){
                String categoryName = CategoryNameEnum.getName(gwCulture.getCategoryId());
                gwCulture.setCategoryName(categoryName);
            }
        }
        gwCulture = GwCulture.getCulture(gwCulture);
        if(StringUtils.isNotEmpty(gwCulture.getStelaClass())){
            String stelaName = getStelaName(gwCulture.getStelaClass());
            gwCulture.setStelaName(stelaName);
        }

        return gwCulture;
    };

    @GetMapping(value = "/list")
    public Page page(Page<GwCulture> page,GwCulture culture){
        return cultureService.pageSearch(page,culture);
    }

    @GetMapping(value = "/sortList")
    public List<Pql> list(){
        return Pql.sortList();
    }


    @GetMapping(value = "/category")
    public List<CultureCategory> categoryList(){
        List<CultureCategory> categoryList =new LinkedList<>();
        List<String> list= Arrays.asList("历史故事","神话传说","人物传奇","长城碑刻");
        for (int i = 0; i < list.size(); i++) {
            CultureCategory category=new CultureCategory();
            Integer num=i+1;
            category.setId(num.toString());
            category.setName(list.get(i));
            categoryList.add(category);
        }
        return categoryList;
    }

    @GetMapping(value = "/stelaClass")
    public List<CultureCategory> stela(){
        QueryWrapper<SysDictValue> wrapper=new QueryWrapper<>();
        wrapper.eq("dict_type_id","3a479546b3c6fea20bb6fd0251ea8efa");
        List<CultureCategory> categoryList =new LinkedList<>();
        List<SysDictValue> list = valueService.list(wrapper);
        for (int i = 0; i < list.size(); i++) {
            CultureCategory category=new CultureCategory();
            category.setId(list.get(i).getValue());
            category.setName(list.get(i).getLabel());
            categoryList.add(category);
        }
        return categoryList;
    }

    @GetMapping(value = "/stelaList")
    public List<SysDictValue> stelaList(){
        QueryWrapper<SysDictValue> wrapper=new QueryWrapper<>();
        wrapper.eq("dict_type_id","3a479546b3c6fea20bb6fd0251ea8efa");
        List<SysDictValue> list = valueService.list(wrapper);
        return list;
    }

    @GetMapping(value = "/stelaClassPage")
    public Page<GwCulture> pageGwCulture(Page<GwCulture> page,GwCulture gwCulture){
        return cultureService.pageGwCulture(page,gwCulture);
    }


    @GetMapping(value = "review/{id}/{status}")
    public void status(@PathVariable String id,@PathVariable Integer status){
        cultureService.setStatus(id,status);
    }


    public  String getStelaName(String stelaId){
        QueryWrapper<SysDictValue> wrapper=new QueryWrapper<>();
        wrapper.eq("dict_type_id","3a479546b3c6fea20bb6fd0251ea8efa");
        wrapper.eq("value",stelaId);
        SysDictValue dictValue = valueService.getOne(wrapper);
        return dictValue.getLabel();
    }


}
