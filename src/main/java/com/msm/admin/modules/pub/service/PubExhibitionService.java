package com.msm.admin.modules.pub.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.common.entity.TreeData;
import com.msm.admin.modules.info.entity.Exhibition;
import com.msm.admin.modules.info.service.ExhibitionService;
import com.msm.admin.modules.system.entity.SysDepart;
import com.msm.admin.modules.system.service.SysDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author quavario@gmail.com
 * @date 2019/12/18 17:15
 */
@Service
public class PubExhibitionService {
    @Autowired
    private ExhibitionService exhibitionService;


    @Autowired
    SysDepartService departService;

    private static final String[] QUERY_LIST_FIELD = {
            "id", "title", "thumb", "pano_thumb", "pano"
    };

    private static final String[] QUERY_ONE_FIELD = {
            "id", "title", "thumb", "start_time", "close_time", "open_time", "dep_id", "content", "pano"
    };


    @Cacheable(value = "pubExhibition", keyGenerator = "cacheKeyGenerator")
    public IPage pageSearch(Page<Exhibition> page, Exhibition exhibition) {
        QueryWrapper<Exhibition> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(exhibition.getTitle())) {
            queryWrapper.like("title", exhibition.getTitle());
        }

        if (!StringUtils.isEmpty(exhibition.getType())) {
            queryWrapper.eq("type", exhibition.getType());
        }

        if (!StringUtils.isEmpty(exhibition.getDepId())) {
            queryWrapper.eq("dep_id", exhibition.getDepId());
        }

        queryWrapper.select(QUERY_LIST_FIELD).eq("status", 1);
        queryWrapper.orderByDesc("update_date", "create_date");
        Page<Exhibition> exhibitionPage = exhibitionService.page(page, queryWrapper);
        return exhibitionPage;
    }

    @Cacheable(value = {"pubExhibition"}, key = "'getById:' + #id")
    public Exhibition getById(String id) {
        QueryWrapper<Exhibition> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(QUERY_ONE_FIELD).eq("id", id).eq("status", 1);
        Exhibition exhibition = exhibitionService.getOne(queryWrapper);

        Optional.ofNullable(exhibition.getDepId()).ifPresent(eId -> exhibition.setDepId(departService.getNameById(eId)));
        return exhibition;
    }

    @Cacheable(value = "pubExhibition", keyGenerator = "cacheKeyGenerator")
    public IPage panoPageSearch(Page<Exhibition> page, Exhibition exhibition) {
        QueryWrapper<Exhibition> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(exhibition.getTitle())) {
            queryWrapper.like("title", exhibition.getTitle());
        }

        if (!StringUtils.isEmpty(exhibition.getType())) {
            queryWrapper.eq("type", exhibition.getType());
        }

        if (!StringUtils.isEmpty(exhibition.getDepId())) {
            queryWrapper.eq("dep_id", exhibition.getDepId());
        }

        if (!StringUtils.isEmpty(exhibition.getAreaId())) {
            List<String> depIds = departService.list(Wrappers.<SysDepart>lambdaQuery()
                    .eq(SysDepart::getAreaId, exhibition.getAreaId()))
                    .stream().map(TreeData::getId).collect(Collectors.toList());
            queryWrapper.in("dep_id", depIds);
        }

        queryWrapper.isNotNull("pano");
        queryWrapper.ne("pano", "");

        queryWrapper.select(QUERY_LIST_FIELD).eq("status", 1);
        queryWrapper.orderByDesc("update_date", "create_date");
        Page<Exhibition> exhibitionPage = exhibitionService.page(page, queryWrapper);
        return exhibitionPage;
    }

    /**
     * 临展列表
     * @param exhibition 展览
     */
    @Cacheable(value = "pubExhibition", keyGenerator = "cacheKeyGenerator")
    public IPage temporaryExhibitionList(Page<Exhibition> page, Exhibition exhibition) {
        LambdaQueryWrapper<Exhibition> queryWrapper = new LambdaQueryWrapper<Exhibition>();
        queryWrapper.eq(Exhibition::getType, 2);
        if (!StringUtils.isEmpty(exhibition.getDepId())) {
            queryWrapper.eq(Exhibition::getDepId, exhibition.getDepId());
        }

        if (!StringUtils.isEmpty(exhibition.getTitle())) {
            queryWrapper.like(Exhibition::getTitle, exhibition.getTitle());
        }

        if (!StringUtils.isEmpty(exhibition.getStartTime())) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(exhibition.getStartTime());
            int year = cal.get(Calendar.YEAR);

            cal.set(year, Calendar.JANUARY, 1);
            Date searchStartTime = cal.getTime();

            cal.set(year + 1, Calendar.JANUARY, 1);
            Date searchEndTime = cal.getTime();

            System.out.println(searchStartTime);
            System.out.println(searchEndTime);
            queryWrapper.gt(Exhibition::getStartTime, searchStartTime);
            queryWrapper.lt(Exhibition::getStartTime, searchEndTime);
        }

        queryWrapper.orderByDesc(Exhibition::getStartTime);
        return exhibitionService.page(page, queryWrapper);
    }
}
