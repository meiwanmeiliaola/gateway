package com.msm.admin.greatWall.gwNum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.greatWall.gwNum.entity.GwNum;
import com.msm.admin.greatWall.gwNum.enums.AreaNameEnum;
import com.msm.admin.greatWall.gwNum.mapper.GwNumMapper;
import com.msm.admin.greatWall.gwNum.service.GwNumService;
import com.msm.admin.utils.SubjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ????(GwNum)表服务实现类
 *
 * @author makejava
 * @since 2022-02-11 14:56:55
 */
@Service("gwNumService")
public class GwNumServiceImpl extends ServiceImpl<GwNumMapper, GwNum> implements GwNumService {

    @Autowired
    private GwNumMapper gwNumDao;

    @Override
    public IPage pageSearch(Page<GwNum> page, GwNum gwNum) {
        QueryWrapper<GwNum> wrapper=new QueryWrapper<>();

        wrapper= SubjectUtils.isAdmin(wrapper);
        if (gwNum !=null){
            if (StringUtils.isNotEmpty(gwNum.getArea())){
                wrapper.eq("area",gwNum.getArea());
            }
            if (StringUtils.isNotEmpty(gwNum.getTitle())){
                wrapper.like("title",gwNum.getTitle());
            }

            //根据审核状态查询
            if (gwNum.getStatus() !=null){
                if (gwNum.getStatus() ==20){
                    wrapper.eq("status",0).or().eq("status",2);
                }else {
                    wrapper.eq("status",gwNum.getStatus());
                }
            }
            if(SubjectUtils.isAdminNull()){
               wrapper.orderByAsc("sort");
            }else {
                wrapper.orderByDesc("create_date");
            }

        }
        Page<GwNum> gwNumPage = gwNumDao.selectPage(page, wrapper);
        List<GwNum> records = gwNumPage.getRecords();
        records.forEach(item ->{
            String areaName = AreaNameEnum.getName(item.getArea());
            item.setAreaName(areaName);
        });
        return gwNumPage;
    }

    @Override
    public void steStatus(String id, Integer status) {
        GwNum num = gwNumDao.selectById(id);
        if (num !=null){
            num.setStatus(status);
            updateById(num);
        }
    }

}

