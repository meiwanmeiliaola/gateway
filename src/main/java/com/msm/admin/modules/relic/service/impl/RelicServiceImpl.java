package com.msm.admin.modules.relic.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.common.entity.TreeData;
import com.msm.admin.modules.common.entity.Years;
import com.msm.admin.modules.common.service.YearsService;
import com.msm.admin.modules.relic.entity.Relic;
import com.msm.admin.modules.relic.entity.RelicCategory;
import com.msm.admin.modules.relic.mapper.RelicMapper;
import com.msm.admin.modules.relic.service.RelicCategoryService;
import com.msm.admin.modules.relic.service.RelicService;
import com.msm.admin.modules.system.entity.SysDepart;
import com.msm.admin.modules.system.entity.SysUser;
import com.msm.admin.modules.system.service.SysAreaService;
import com.msm.admin.modules.system.service.SysDepartService;
import com.msm.admin.modules.system.service.SysDictValueService;
import com.msm.admin.modules.system.service.SysUserService;
import com.msm.admin.utils.PqlUtil;
import com.msm.admin.utils.SubjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import com.msm.admin.modules.common.service.impl.RedisService;

/**
 * @Description: area
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
@Slf4j
public class RelicServiceImpl extends ServiceImpl<RelicMapper, Relic> implements RelicService {
    private static final String[] QUERY_LIST_FIELD = {
            "id", "name", "images", "thumb", "dep_id", "category_id", "create_date", "status", "update_date", "create_by", "url_3d"
    };
    @Autowired
    private SysDepartService departService;

    @Autowired
    private RelicMapper relicMapper;

    @Autowired
    private YearsService yearsService;

    @Autowired
    private RelicCategoryService categoryService;

    @Autowired
    private SysUserService userService;

    @Override
    public List<Relic> list(Wrapper<Relic> queryWrapper) {
        List<Relic> list = super.list(queryWrapper);
        return list;
    }

    /**
     * 文物分页列表查询
     * @param page 分页参数
     * @param relic 检索信息
     * @return page分页
     */
    @Override
    public IPage pageSearch(Page<Relic> page, @NotEmpty Relic relic) {


        QueryWrapper<Relic> queryWrapper = new QueryWrapper<>();
        // 禁止查询所有文物
        if (!SubjectUtils.isAdmin() && relic.getType() == null) {
            return null;
        }

        if (!SubjectUtils.isAdmin()) {
//            queryWrapper.eq("create_by", SubjectUtils.getCurrentUser().getId());
            queryWrapper.eq("dep_id", SubjectUtils.getCurrentUser().getDepId());
        }

        Subject subject = SecurityUtils.getSubject();

        // check permissions
        switch (relic.getType()) {
            case 1:
                subject.checkPermission("relic:common:list");
                break;
            case 2:
                subject.checkPermission("relic:treasure:list");
                break;
            case 3:
                subject.checkPermission("relic:3d:list");
                break;
            default:
                break;
        }

        // 按名称查询
        if (!StringUtils.isEmpty(relic.getName())) {
            queryWrapper.like("name", relic.getName());

        }

        // 按馆藏单位查询
        if (!StringUtils.isEmpty(relic.getDepId())) {
            queryWrapper.eq("dep_id", relic.getDepId());

        }

        // 按状态查询
        if (!StringUtils.isEmpty(relic.getStatus())) {
            queryWrapper.eq("status", relic.getStatus());
        }

        // 按类别查询
        if (!StringUtils.isEmpty(relic.getCategoryId())) {
            // 查询父级餐袋
            String categoryId = relic.getCategoryId();

            List<String> childIds = categoryService.getListByParentId(categoryId).stream().map(RelicCategory::getId).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(childIds)) {
                queryWrapper.and(c -> c.eq("category_id", categoryId));
            } else {
                queryWrapper.and(c -> c.eq("category_id", categoryId).or().in("category_id", childIds));
            }

        }

        // 按文物年代查询
        if (!StringUtils.isEmpty(relic.getYearsId())) {
            String yearsId = relic.getYearsId();
            List<String> yearsIds = yearsService.list(
                    Wrappers.<Years>lambdaQuery()
                            .select(Years::getId)
                            .eq(Years::getParentId, yearsId)
            ).stream().map(Years::getId).collect(Collectors.toList());

            if (yearsIds.size() > 0) {
                queryWrapper.and(c -> c.eq("years_id", yearsId).or().in("years_id", yearsIds));
            } else {
                queryWrapper.eq("years_id", yearsId);
            }

        }

        // 按类别查询
        if (!StringUtils.isEmpty(relic.getType())) {

            if (relic.getType() == 456) {
                // 查询全部革命文物接口
                ArrayList<Integer> revList = new ArrayList<>();
                revList.add(4);
                revList.add(5);
                revList.add(6);
                queryWrapper.in("type", revList);
            } else {
                queryWrapper.eq("type", relic.getType());
            }
        }

        if (!StringUtils.isEmpty(relic.getStatus())) {

            switch (relic.getStatus()) {
                // 1 审核通过 首页显示
                case 1:
                    // 2. 审核不通过
                case 2:
                    // 3.已发布
                case 3:
                    // 4. 草稿
                case 4:
                    queryWrapper.eq("status", relic.getStatus());
                    break;
                case 23:
                    queryWrapper.eq("status", 2).or().eq("status", 3);
                    break;
                default:
                    break;
            }
        }

        // 不要用后端逻辑控制前端页面
        // queryWrapper.eq("status", 1);

        queryWrapper.orderByDesc("create_date");

        queryWrapper.select(QUERY_LIST_FIELD);
        Page<Relic> relicPage = super.page(page, queryWrapper);
        List<Relic> records = relicPage.getRecords();
        records.forEach(item -> {
            // 设置部门名称
            String depName = departService.getNameById(item.getDepId());
            item.setDepName(depName);


            // 设置类别
            RelicCategory category = categoryService.getById(item.getCategoryId());
            if (category != null && !StringUtils.isEmpty(category.getName())) {
                item.setCategoryName(category.getName());
            }

            // 设置创建人
            String userId = item.getCreateBy();
            item.setCreateBy(userService.getNameById(userId));


        });

        return relicPage;
    }

    @Override
    public Map getInfo() {
        Map<String, List> map = new HashMap<>();



        Map<String, List<Map<String, String>>> info = relicMapper.selectRelicInfo().stream().collect(Collectors.groupingBy(m -> m.get("type")));
        map.putAll(info);
        map.put("pqlId",PqlUtil.pqlList());
        map.put("GwCate",PqlUtil.list());


        List<RelicCategory> list = categoryService.list(
                Wrappers.<RelicCategory>lambdaQuery()
                        .select(RelicCategory::getId, RelicCategory::getParentId, RelicCategory::getName, RelicCategory::getType)
        );
        List relicCategories = TreeData.toTreeList(list);
        map.put("category", relicCategories);
        return map;
/*
        // material
        List<SysDictValue> material = dictValueService.list(Wrappers.<SysDictValue>lambdaQuery()
                .select(SysDictValue::getLabel, SysDictValue::getValue)
                .eq(SysDictValue::getDictTypeId, "be9cf44299c14263b2ba249987a9f524"));

        // level
        List<SysDictValue> level = dictValueService.list(Wrappers.<SysDictValue>lambdaQuery()
                .select(SysDictValue::getLabel, SysDictValue::getValue)
                .eq(SysDictValue::getDictTypeId, "4dba05bebf0645609693bc6addaf912c"));

        // source
        List<SysDictValue> source = dictValueService.list(Wrappers.<SysDictValue>lambdaQuery()
                .select(SysDictValue::getLabel, SysDictValue::getValue)
                .eq(SysDictValue::getDictTypeId, "d05550e3fed64c5a955207b810e94119"));

        // integrity
        List<SysDictValue> integrity = dictValueService.list(Wrappers.<SysDictValue>lambdaQuery()
                .select(SysDictValue::getLabel, SysDictValue::getValue)
                .eq(SysDictValue::getDictTypeId, "fae536a171bb4148898fb4936d8b1f55"));

        // collect date
        List<SysDictValue> collectDate = dictValueService.list(Wrappers.<SysDictValue>lambdaQuery()
                .select(SysDictValue::getLabel, SysDictValue::getValue)
                .eq(SysDictValue::getDictTypeId, "7f3cfe26d16c47508adaa3d2dffebeb9"));

        // 保存状态
        List<SysDictValue> preservationState = dictValueService.list(Wrappers.<SysDictValue>lambdaQuery()
                .select(SysDictValue::getLabel, SysDictValue::getValue)
                .eq(SysDictValue::getDictTypeId, "f8f46f1604fa482fa804fbd5c60a83cc"));

        // area
        List<SysDictValue> area = areaService.list(
                Wrappers.<SysArea>lambdaQuery()
                        .select(SysArea::getId, SysArea::getName)
                        .eq(SysArea::getParentId, "1")
        ).stream().map(item -> {
            SysDictValue dictValue = new SysDictValue();
            return  dictValue.setValue(item.getId()).setLabel(item.getName());
        }).collect(Collectors.toList());

        // depart
        List<SysDictValue> depart = departService.list(
                Wrappers.<SysDepart>lambdaQuery()
                        .select(SysDepart::getId, SysDepart::getName)
                        .eq(SysDepart::getParentId, "1")
        ).stream().map(item -> {
            SysDictValue dictValue = new SysDictValue();
            return  dictValue.setValue(item.getId()).setLabel(item.getName());
        }).collect(Collectors.toList());

        // years
        List<SysDictValue> years = yearsService.list(
                Wrappers.<Years>lambdaQuery()
                        .select(Years::getId, Years::getName)
                        .eq(Years::getType, 2)
        ).stream().map(item -> {
            SysDictValue dictValue = new SysDictValue();
            return  dictValue.setValue(item.getId()).setLabel(item.getName());
        }).collect(Collectors.toList());

        // category
        List<RelicCategory> list = categoryService.list(
                Wrappers.<RelicCategory>lambdaQuery()
                        .select(RelicCategory::getId, RelicCategory::getParentId, RelicCategory::getName, RelicCategory::getType)
        );
        List relicCategories = TreeData.toTreeList(list);

        map.put("material", material);
        map.put("level", level);
        map.put("source", source);
        map.put("preservationState", preservationState);
        map.put("integrity", integrity);
        map.put("collectDate", collectDate);
        map.put("area", area);
        map.put("depart", depart);
        map.put("years", years);
        map.put("category", relicCategories);

        return map;*/
    }

    @Override
    public IPage reviewSearch(Page<Relic> page, Relic relic) {
        QueryWrapper<Relic> queryWrapper = new QueryWrapper<>();
//        queryWrapper.and(i -> i.eq("status", 2).or(q -> q.eq("status", 3)));
        if (relic != null) {
            // 按名称查询
            if (!StringUtils.isEmpty(relic.getName())) {
                queryWrapper.and(i -> i.like("name", relic.getName()));
            }

            // 按馆藏单位查询
            if (!StringUtils.isEmpty(relic.getDepId())) {
                queryWrapper.eq("dep_id", relic.getDepId());

            }

            // 按状态查询
            if (!StringUtils.isEmpty(relic.getStatus())) {
                queryWrapper.eq("status", relic.getStatus());
            }

            // 按类别查询
            if (!StringUtils.isEmpty(relic.getCategoryId())) {
                // 查询父级餐袋
                String categoryId = relic.getCategoryId();

                List<String> childIds = categoryService.getListByParentId(categoryId).stream().map(RelicCategory::getId).collect(Collectors.toList());

                if (CollectionUtils.isEmpty(childIds)) {
                    queryWrapper.and(c -> c.eq("category_id", categoryId));
                } else {
                    queryWrapper.and(c -> c.eq("category_id", categoryId).or().in("category_id", childIds));
                }

            }

            // 按文物年代查询
            if (!StringUtils.isEmpty(relic.getYearsId())) {
                String yearsId = relic.getYearsId();
                List<String> yearsIds = yearsService.list(
                        Wrappers.<Years>lambdaQuery()
                                .select(Years::getId)
                                .eq(Years::getParentId, yearsId)
                ).stream().map(Years::getId).collect(Collectors.toList());

                if (yearsIds.size() > 0) {
                    queryWrapper.and(c -> c.eq("years_id", yearsId).or().in("years_id", yearsIds));
                } else {
                    queryWrapper.eq("years_id", yearsId);
                }
            }

            // 按类型查询
            if (!StringUtils.isEmpty(relic.getType())) {

                if (relic.getType() == 456) {
                    // 查询全部革命文物接口
                    ArrayList<Integer> revList = new ArrayList<>();
                    revList.add(4);
                    revList.add(5);
                    revList.add(6);
                    queryWrapper.in("type", revList);
                } else {
                    queryWrapper.eq("type", relic.getType());
                }
            }
            queryWrapper.and(i-> i.eq("status", 2).or().eq("status", 3));
            queryWrapper.orderByDesc("status", "update_date");
        }


        Page<Relic> relicPage = super.page(page, queryWrapper);

        List<Relic> records = relicPage.getRecords();
/*        records.forEach(item -> {
            // 设置部门名称
            String depName = departService.getNameById(item.getDepId());
            item.setDepName(depName);


            // 设置类别
            RelicCategory category = categoryService.getById(item.getCategoryId());
            if (category != null && !StringUtils.isEmpty(category.getName())) {
                item.setCategoryName(category.getName());
            }

            // 设置创建人
            String userId = item.getCreateBy();
            item.setCreateBy(userService.getNameById(userId));
        })*/
        return null;
    }

    @Override
    public void setStatus(String id, Integer status) {

    }

    @Override
    public IPage pageTest(Page<Relic> page, Relic relic) {

        QueryWrapper<Relic> queryWrapper = new QueryWrapper<>();
        // 禁止查询所有文物
/*
        if (!SubjectUtils.isAdmin() && relic.getType() == null) {
            return null;
        }
*/

        if (!SubjectUtils.isAdmin()) {
//            queryWrapper.eq("create_by", SubjectUtils.getCurrentUser().getId());
            queryWrapper.eq("dep_id", SubjectUtils.getCurrentUser().getDepId());
        }

        Subject subject = SecurityUtils.getSubject();

        // check permissions
        switch (relic.getType()) {
            case 1:
                subject.checkPermission("relic:common:list");
                break;
            case 2:
                subject.checkPermission("relic:treasure:list");
                break;
            case 3:
                subject.checkPermission("relic:3d:list");
                break;
            default:
                break;
        }

        // 按名称查询
        if (!StringUtils.isEmpty(relic.getName())) {
            queryWrapper.like("name", relic.getName());

        }

        // 按馆藏单位查询
        if (!StringUtils.isEmpty(relic.getDepId())) {
            queryWrapper.eq("dep_id", relic.getDepId());

        }

        // 按状态查询
        if (!StringUtils.isEmpty(relic.getStatus())) {
            queryWrapper.eq("status", relic.getStatus());
        }

        // 按类别查询
        if (!StringUtils.isEmpty(relic.getCategoryId())) {
            // 查询父级餐袋
            String categoryId = relic.getCategoryId();

            List<String> childIds = categoryService.getListByParentId(categoryId).stream().map(RelicCategory::getId).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(childIds)) {
                queryWrapper.and(c -> c.eq("category_id", categoryId));
            } else {
                queryWrapper.and(c -> c.eq("category_id", categoryId).or().in("category_id", childIds));
            }

        }

        // 按文物年代查询
        if (!StringUtils.isEmpty(relic.getYearsId())) {
            String yearsId = relic.getYearsId();
            List<String> yearsIds = yearsService.list(
                    Wrappers.<Years>lambdaQuery()
                            .select(Years::getId)
                            .eq(Years::getParentId, yearsId)
            ).stream().map(Years::getId).collect(Collectors.toList());

            if (yearsIds.size() > 0) {
                queryWrapper.and(c -> c.eq("years_id", yearsId).or().in("years_id", yearsIds));
            } else {
                queryWrapper.eq("years_id", yearsId);
            }

        }

        // 按类别查询
        if (!StringUtils.isEmpty(relic.getType())) {

            if (relic.getType() == 456) {
                // 查询全部革命文物接口
                ArrayList<Integer> revList = new ArrayList<>();
                revList.add(4);
                revList.add(5);
                revList.add(6);
                queryWrapper.in("type", revList);
            } else {
                queryWrapper.eq("type", relic.getType());
            }
        }

        if (!StringUtils.isEmpty(relic.getStatus())) {

            switch (relic.getStatus()) {
                // 1 审核通过 首页显示
                case 1:
                    // 2. 审核不通过
                case 2:
                    // 3.已发布
                case 3:
                    // 4. 草稿
                case 4:
                    queryWrapper.eq("status", relic.getStatus());
                    break;
                case 23:
                    queryWrapper.eq("status", 2).or().eq("status", 3);
                    break;
                default:
                    break;
            }
        }

        // 不要用后端逻辑控制前端页面
        // queryWrapper.eq("status", 1);

        queryWrapper.orderByDesc("create_date");

        queryWrapper.select(QUERY_LIST_FIELD);
        Page<Relic> relicPage = super.page(page, queryWrapper);
        List<Relic> records = relicPage.getRecords();
        records.forEach(item -> {
            // 设置部门名称
            String depName = departService.getNameById(item.getDepId());
            item.setDepName(depName);


            // 设置类别
            RelicCategory category = categoryService.getById(item.getCategoryId());
            if (category != null && !StringUtils.isEmpty(category.getName())) {
                item.setCategoryName(category.getName());
            }

            // 设置创建人
            String userId = item.getCreateBy();
            item.setCreateBy(userService.getNameById(userId));


        });

        return relicPage;
    }
}


     