package com.msm.admin.modules.pub.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.common.entity.TreeData;
import com.msm.admin.modules.common.entity.Years;
import com.msm.admin.modules.common.service.YearsService;
import com.msm.admin.modules.common.service.impl.RedisService;
import com.msm.admin.modules.relic.entity.Relic;
import com.msm.admin.modules.relic.entity.RelicCategory;
import com.msm.admin.modules.relic.mapper.RelicMapper;
import com.msm.admin.modules.relic.service.RelicCategoryService;
import com.msm.admin.modules.relic.service.RelicService;
import com.msm.admin.modules.system.entity.SysDepart;
import com.msm.admin.modules.system.entity.SysDictValue;
import com.msm.admin.modules.system.service.SysDepartService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.msm.admin.modules.relic.RelicTypeEnum.ALL_REV;

/**
 * @author quavario@gmail.com
 * @date 2019/12/18 17:15
 */
@Service
public class PubRelicService {
    @Autowired
    private RelicService relicService;

    @Autowired
    private YearsService yearsService;

    @Autowired
    private RelicCategoryService relicCategoryService;

    @Autowired
    private PubDictValueService dictValueService;

    @Autowired
    private SysDepartService departService;


    @Autowired
    private RelicMapper relicMapper;
    @Autowired
    RedisService redisService;


    private static final String[] QUERY_LIST_FIELD = {
            "id", "name", "images", "thumb", "url_3d", "pano", "years_id", "type", "dep_id"
    };

    private static final String[] QUERY_ONE_FIELD = {
            "id", "name", "images", "thumb", "url_3d", "pano",
            "years_id", "dep_id", "content", "level_id", "mat_id"
    };

//    @Cacheable(value = "pubRelic", keyGenerator = "cacheKeyGenerator")
//    public IPage pageSearch(Page<Relic> page, Relic relic) {
//        QueryWrapper<Relic> queryWrapper = new QueryWrapper<>();
//        if (relic != null) {
//            if (!StringUtils.isEmpty(relic.getName())) {
//                queryWrapper.likeRight("name", relic.getName());
//            }
//            // 按类别查询
//            if (!StringUtils.isEmpty(relic.getType())) {
//                if (relic.getType() == 456) {
//                    // 查询全部革命文物接口
//                    ArrayList<Integer> revList = new ArrayList<>();
//                    revList.add(4);
//                    revList.add(5);
//                    revList.add(6);
//                    queryWrapper.in("type", revList);
//                } else {
//                    queryWrapper.eq("type", relic.getType());
//                }
//            }
//
//            if (!StringUtils.isEmpty(relic.getDepId())) {
//                queryWrapper.eq("dep_id", relic.getDepId());
//            }
//            if (!StringUtils.isEmpty(relic.getCategoryId())) {
//                List<String> ids = relicCategoryService.getListByParentId(relic.getCategoryId())
//                        .stream().map(TreeData::getId)
//                        .collect(Collectors.toList());
//
//                queryWrapper.in("category_id", ids);
//            }
//
//            // 按年代查询
//            if (!StringUtils.isEmpty(relic.getYearsId())) {
//                String yearsId = relic.getYearsId();
//                List<String> parentYearsId = yearsService.getListByParentId(yearsId).stream().map(Years::getId).collect(Collectors.toList());
//
//
//                if (parentYearsId.size() > 0) {
//                    queryWrapper.and(c -> c.eq("years_id", yearsId).or().in("years_id", parentYearsId));
//                } else {
//                    queryWrapper.eq("years_id", yearsId);
//                }
//            }
//        }
//        queryWrapper.orderByDesc("sort", "update_date");
//        queryWrapper.eq("status", 1).select(QUERY_LIST_FIELD);
//
//        IPage<Relic> relicIPage = relicService.page(page, queryWrapper);
//        relicIPage.getRecords().forEach(item -> {
//            if (!StringUtils.isEmpty(item.getDepId())) {
//                item.setDepName(departService.getNameById(item.getDepId()));
//                item.setDepId(null);
//            }
//        });
//
//        return relicIPage;
//    }

    @Cacheable(value = "pubRelic", keyGenerator = "cacheKeyGenerator")
    public IPage pageSearch(Page<Relic> page, Relic relic) {
        LambdaQueryWrapper<Relic> queryWrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isEmpty(relic.getName())) {
            queryWrapper.like(Relic::getName, relic.getName());
        }
        // 按类别查询
        if (!StringUtils.isEmpty(relic.getType())) {
            if (relic.getType() == 456) {
                // 查询全部革命文物接口
                queryWrapper.in(Relic::getType, Arrays.asList(4, 5, 6));
            } else {
                queryWrapper.eq(Relic::getType, relic.getType());
            }
        }

        // 部门检索
        if (!StringUtils.isEmpty(relic.getDepId())) {
            queryWrapper.eq(Relic::getDepId, relic.getDepId());
        }

        // 类别检索
        if (!StringUtils.isEmpty(relic.getCategoryId())) {
            List<String> ids = relicCategoryService.getListByParentId(relic.getCategoryId())
                    .stream().map(TreeData::getId)
                    .collect(Collectors.toList());
            queryWrapper.in(Relic::getCategoryId, ids);
        }

        // 按年代查询
        if (!StringUtils.isEmpty(relic.getYearsId())) {
            String yearsId = relic.getYearsId();
            List<String> parentYearsId = yearsService.getListByParentId(yearsId).stream().map(Years::getId).collect(Collectors.toList());


            if (parentYearsId.size() > 0) {
                queryWrapper.and(c -> c.eq(Relic::getYearsId, yearsId).or().in(Relic::getYearsId, parentYearsId));
            } else {
                queryWrapper.eq(Relic::getYearsId, yearsId);
            }
        }

        // queryWrapper.orderByDesc(Relic::getSort, Relic::getUpdateDate);
        queryWrapper.eq(Relic::getStatus, 1).select(
                Relic::getId, Relic::getName, Relic::getImages, Relic::getThumb, Relic::getUrl3d, Relic::getPano, Relic::getYearsId, Relic::getType, Relic::getDepId, Relic::getSort
        );
        // 按照sort升序， update_date 降序
        queryWrapper.last("order by case when sort is null then 1 else 0 end asc, sort asc, create_date desc");


        IPage<Relic> relicIPage = relicService.page(page, queryWrapper);
        relicIPage.getRecords().forEach(item -> {
            if (!StringUtils.isEmpty(item.getDepId())) {
                item.setDepName(departService.getNameById(item.getDepId()));
                item.setDepId(null);
            }
        });

        return relicIPage;
    }

    @Cacheable(value = {"pubRelic"}, key = "'getById:' + #id")
    public Relic getById(String id) {
        Relic relic = relicMapper.pubSelectById(id);
        List<String> images = relic.getImages();
        if (!CollectionUtils.isEmpty(images)) {
            for (int i = 0; i < images.size(); i++) {
                images.set(i, "/watermark" + images.get(i));
            }
        }
        return relic;
    }

    /**
     * TODO
     * 文物属性信息列表
     * @return
     */
    @Cacheable(value = {"pubRelic"}, key = "'info'", cacheManager = "dictCacheManager")
    public Map<String, List<SysDictValue>> getInfos() {

        Map<String, List<SysDictValue>> infos = new HashMap<>();
        // 文物年代列表
        List<SysDictValue> years = dictValueService.getByTypeId("e3bcefcf527941c3af9fb3b37d9eed79");

        // 文物类别列表
        List<SysDictValue> categories = relicCategoryService.list(
                Wrappers.<RelicCategory>lambdaQuery().eq(RelicCategory::getType, 2)
                        .eq(RelicCategory::getStatus, 1)
                        .select(RelicCategory::getId, RelicCategory::getName, RelicCategory::getSort)
        ).stream().map(relicCategory ->
                new SysDictValue()
                .setValue(relicCategory.getId()).setLabel(relicCategory.getName())
                .setSort(relicCategory.getSort()))
        .collect(Collectors.toList());

        // 馆藏单位
        List<SysDictValue> departs = departService.list(
                Wrappers.<SysDepart>lambdaQuery().eq(SysDepart::getStatus, 1)
                        .select(SysDepart::getId, SysDepart::getName, SysDepart::getSort)
        ).stream().map(sysDepart -> new SysDictValue()
                .setLabel(sysDepart.getName()).setValue(sysDepart.getId())
                .setSort(sysDepart.getSort()))
        .collect(Collectors.toList());


        infos.put("years", years);
        infos.put("categories", categories);
        infos.put("departs", departs);

        return infos;

    }
}
