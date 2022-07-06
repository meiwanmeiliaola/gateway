package com.msm.admin.greatWall.overview.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.common.enums.ErrorInfo;
import com.msm.admin.greatWall.gwNum.entity.GwNum;
import com.msm.admin.greatWall.overview.entity.Affiliation;
import com.msm.admin.greatWall.overview.entity.GwOverview;
import com.msm.admin.greatWall.overview.enums.AffiliationEnum;
import com.msm.admin.greatWall.overview.service.OverviewService;
import com.msm.admin.modules.common.entity.TreeData;
import com.msm.admin.modules.relic.entity.RelicCategory;
import com.msm.admin.modules.relic.mapper.RelicCategoryMapper;
import com.msm.admin.modules.relic.mapper.RelicMapper;
import com.msm.admin.modules.relic.service.RelicCategoryService;
import com.msm.admin.modules.statistics.mapper.RelicStatisticsMapper;
import com.msm.admin.modules.system.entity.SysUser;
import com.msm.admin.utils.DateUtil;
import com.msm.admin.utils.ExceptionThrower;
import com.msm.admin.utils.SubjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zxy
 * @date 2022/2/11 11:25
 */
@RestController
@RequestMapping(value = "/overview")
public class OverviewController {

    @Autowired
    private OverviewService overviewService;

    @Autowired
    private RelicCategoryService categoryService;

    @Autowired
    private RelicMapper relicMapper;

    @Autowired
    private RelicStatisticsMapper statisticsMapper;



    @PutMapping
    public GwOverview save(@RequestBody GwOverview overview){
        QueryWrapper<GwOverview> wrapper=new QueryWrapper<>();

        //判断部门是否存在
        SysUser user = SubjectUtils.getCurrentUser();
        if (StringUtils.isEmpty(overview.getDepId())){
            overview.setDepId(user.getDepId());
            wrapper.eq("dep_id",user.getDepId());
        }else {
            wrapper.eq("dep_id",overview.getDepId());
        }


        List<Object> list;
        wrapper.eq("title",overview.getTitle());
        wrapper.eq("affiliation",overview.getAffiliation());

        String affiliationId="4";

        if(StringUtils.isNotEmpty(overview.getCreateDate())){
            overview.setUpdateDate(DateUtil.getNowTime());
        }else {
            //获取当前时间
            overview.setCreateDate(DateUtil.getNowTime());
        }
        if (affiliationId.equals(overview.getAffiliation())){
            overview.setPid("1");
        }

        if (StringUtils.isNotEmpty(overview.getId())){
            wrapper.ne("id",overview.getId());
            list= overviewService.listObjs(wrapper);
        }else {
            list=overviewService.listObjs(wrapper);
        }

        boolean flag = list.size() ==0;
        if (flag){
            overviewService.saveOrUpdate(overview);
        }else {
            ExceptionThrower.of(ErrorInfo.TITLE_EXIST);
        }

        return overview;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id){
        overviewService.removeById(id);
    }

    @GetMapping(value = "/{id}")
    public GwOverview getById(@PathVariable String id){
        GwOverview overview = overviewService.getById(id);
        if (overview !=null){
            if (StringUtils.isNotEmpty(overview.getAffiliation())){
                String affiliationName = AffiliationEnum.getName(overview.getAffiliation());
                overview.setAffiliationName(affiliationName);
            }
            String type="material";
            if (StringUtils.isNotEmpty(overview.getMaterialId())){
                String cateName = statisticsMapper.getByMaterialId(overview.getMaterialId(),type);
                overview.setMaterialName(cateName);
            }
        }
        return overview;
    }

    /**
    * 根据名称和所属长城查询
     * @affiliation: 1: 中国长城  2:明长城  3:河北长城 4战国长城
    * */
    @GetMapping(value = "/query")
    public GwOverview getByName(String title,String affiliation){
        QueryWrapper<GwOverview> wrapper=new QueryWrapper<>();
        GwOverview overview = new GwOverview();
        String affiliationName="战国长城";

        if (StringUtils.isNotEmpty(affiliation)){
            wrapper.eq("affiliation",affiliation);
        }
        wrapper.eq("title",title);

/*        if (StringUtils.isNotEmpty(title)){
            //如果是战国长城,不根据标题查询,根据affiliation查询
            if (affiliationName.equals(title)){
                List<GwOverview> gwOverviewList =overviewService.selectListByAffiliation(wrapper);
                overview.setGwOverviewList(gwOverviewList);
                return overview;
            }

        }*/

        overview = overview(wrapper);

        return overview;
    }



    @GetMapping(value = "/list")
    public Page page(Page<GwOverview> page,GwOverview gwOverview){
        return overviewService.pageSearch(page,gwOverview);
    }


   @GetMapping(value = "cateId")
    public List<Affiliation> list(){
         List<Affiliation> list=new LinkedList<>();
         List<String> stringList= Arrays.asList("中国长城","明长城","河北长城");
        for (int i = 1; i < stringList.size()+1; i++) {
            Integer num=i;
            Affiliation affiliation=new Affiliation();
            affiliation.setId(num.toString());
            affiliation.setName(stringList.get(i-1));
            affiliation.setParentId("0");
            list.add(affiliation);
        }
        Affiliation affiliation=new Affiliation();
        affiliation.setId("4");
        affiliation.setName("战国长城");
        affiliation.setParentId("1");
        list.add(affiliation);
        List<Affiliation> affiliationList = TreeData.toTreeList(list);
        return affiliationList;
    }

    @GetMapping(value = "cate")
    public List<Affiliation> affiliationList(){
        List<Affiliation> list=new LinkedList<>();
        List<String> stringList= Arrays.asList("中国长城","明长城","河北长城","战国长城");
        for (int i = 1; i < stringList.size()+1; i++) {
            Integer num=i;
            Affiliation affiliation=new Affiliation();
            affiliation.setId(num.toString());
            affiliation.setName(stringList.get(i-1));
            affiliation.setParentId("0");
            list.add(affiliation);
        }
        return list;
    }

    @GetMapping(value = "materialId")
    public List<Map<String, String>> materials(){
        Map<String, List<Map<String, String>>> info = relicMapper.selectRelicInfo().stream().collect(Collectors.groupingBy(m -> m.get("type")));
        List<Map<String, String>> mapList = info.get("material");
        return mapList;
    }

    @GetMapping(value = "/review/{id}/{status}")
    public void status(@PathVariable String id,@PathVariable Integer status){
        overviewService.setStatus(id,status);
    }

    public  GwOverview overview(QueryWrapper<GwOverview> wrapper){
        GwOverview  gwOverview = overviewService.getOne(wrapper);

        if (gwOverview !=null){
            String type="material";
            String cateName = statisticsMapper.getByMaterialId(gwOverview.getMaterialId(),type);
/*            List<GwOverview> gwOverviewList =overviewService.selectListByPid(gwOverview);
            if (gwOverviewList !=null){
                gwOverview.setGwOverviewList(gwOverviewList);
            }*/
            if (StringUtils.isNotEmpty(gwOverview.getAffiliation())){
                String affiliationName = AffiliationEnum.getName(gwOverview.getAffiliation());
                gwOverview.setAffiliationName(affiliationName);
            }
            gwOverview.setMaterialName(cateName);
        }
        return gwOverview;
    }





}
