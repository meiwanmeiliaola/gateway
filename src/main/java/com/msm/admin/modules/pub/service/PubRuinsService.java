package com.msm.admin.modules.pub.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.app.entity.Ruins;
import com.msm.admin.modules.app.service.RuinsService;
import com.msm.admin.modules.common.entity.Years;
import com.msm.admin.modules.common.service.YearsService;
import com.msm.admin.modules.system.entity.SysArea;
import com.msm.admin.modules.system.service.SysAreaService;
import com.msm.admin.modules.system.service.SysDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author quavario@gmail.com
 * @date 2019/12/18 17:15
 */
@Service
public class PubRuinsService {
    @Autowired
    private RuinsService ruinsService;

    @Autowired
    private YearsService yearsService;
    

    @Autowired
    private SysDepartService departService;

    @Autowired
    private SysAreaService areaService;


    private static final String[] QUERY_LIST_FIELD = {
            "id", "title", "thumb", "content"
    };

    private static final String[] QUERY_ONE_FIELD = {
            "id", "title", "thumb", "content"
    };
    

    public IPage pageSearch(Page<Ruins> page, Ruins ruins) {
        QueryWrapper<Ruins> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(ruins.getTitle())) {
            queryWrapper.like("title", ruins.getTitle());
        }

        if (!StringUtils.isEmpty(ruins.getCategoryId())) {
            queryWrapper.eq("category_id", ruins.getCategoryId());
        }
        if (!StringUtils.isEmpty(ruins.getAreaId())) {
            queryWrapper.eq("area_id", ruins.getAreaId());
        }

        // 按年代查询
        if (!StringUtils.isEmpty(ruins.getYearsId())) {
            String yearsId = ruins.getYearsId();
            List<String> resultYearsIds = yearsService.getListByParentId(yearsId).stream().map(Years::getId).collect(Collectors.toList());
            /**
             * yearsId可能是子节点，索引要查询自己
             */
            resultYearsIds.add(yearsId);


            // TODO 只能设置子年代
            /*if (resultYearsIds.size() == 0) {
                return null;
            }*/
            queryWrapper.in("years_id", resultYearsIds);

        }
        queryWrapper.select(QUERY_LIST_FIELD).eq("status", 1);
        queryWrapper.orderByDesc("update_date", "create_date");

        IPage<Ruins> ruinsIPage = ruinsService.page(page, queryWrapper);
        return ruinsIPage;
    }

    public Ruins getById(String id) {
        QueryWrapper<Ruins> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(QUERY_ONE_FIELD).eq("id", id).eq("status", 1);
        return ruinsService.getOne(queryWrapper);
    }

    public Map<String, Object> info() {
        Map map = new HashMap();
        // area
        List<SysArea> areas = areaService.list();

        map.put("areas", areas);
        return map;
    }
}
