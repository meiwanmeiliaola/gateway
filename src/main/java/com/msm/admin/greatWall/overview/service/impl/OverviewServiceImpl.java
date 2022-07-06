package com.msm.admin.greatWall.overview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.greatWall.overview.entity.GwOverview;
import com.msm.admin.greatWall.overview.mapper.OverviewMapper;
import com.msm.admin.greatWall.overview.service.OverviewService;
import com.msm.admin.utils.SubjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxy
 * @date 2022/2/11 11:33
 */
@Service
public class OverviewServiceImpl extends ServiceImpl<OverviewMapper, GwOverview> implements OverviewService {

    @Autowired
    private OverviewMapper overviewMapper;

    @Override
    public Page pageSearch(Page<GwOverview> page, GwOverview gwOverview) {

        QueryWrapper<GwOverview> queryWrapper =new QueryWrapper<>();

        queryWrapper= SubjectUtils.isAdmin(queryWrapper);

        if (gwOverview !=null){
            if (StringUtils.isNotEmpty(gwOverview.getTitle())){
                queryWrapper.like("title",gwOverview.getTitle());
            }
            if (StringUtils.isNotEmpty(gwOverview.getMaterialId())){
                queryWrapper.eq("material_id",gwOverview.getMaterialId());
            }
            if (StringUtils.isNotEmpty(gwOverview.getAffiliation())){
                queryWrapper.eq("affiliation",gwOverview.getAffiliation());
            }
            if (gwOverview.getStatus() !=null){
                if (gwOverview.getStatus() ==20){
                    queryWrapper.eq("status",0).or().eq("status",2);
                }else {
                    queryWrapper.eq("status",gwOverview.getStatus());
                }
            }


        }
        queryWrapper.orderByDesc("create_date");

        Page<GwOverview> overviewPage = overviewMapper.selectPage(page, queryWrapper);

        return overviewPage;
    }

    @Override
    public List<GwOverview> selectListByPid(GwOverview gwOverview) {
        QueryWrapper<GwOverview> queryWrapper =new QueryWrapper();
        queryWrapper.eq("pid",gwOverview.getPid());
        List<GwOverview> list = overviewMapper.selectList(queryWrapper);

        return list;
    }

    @Override
    public void setStatus(String id, Integer status) {
        GwOverview overview = overviewMapper.selectById(id);
        if (overview !=null){
            overview.setStatus(status);
            updateById(overview);
        }
    }

    @Override
    public List<GwOverview> selectListByAffiliation(QueryWrapper<GwOverview> queryWrapper) {

        List<GwOverview> gwOverviewList = overviewMapper.selectList(queryWrapper);
        return gwOverviewList;
    }
}
