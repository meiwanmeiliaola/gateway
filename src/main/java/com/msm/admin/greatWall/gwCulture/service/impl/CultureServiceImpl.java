package com.msm.admin.greatWall.gwCulture.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.greatWall.gwCulture.entity.GwCulture;
import com.msm.admin.greatWall.gwCulture.mapper.CultureMapper;
import com.msm.admin.greatWall.gwCulture.service.CultureService;
import com.msm.admin.modules.system.entity.SysDictValue;
import com.msm.admin.modules.system.mapper.SysDictValueMapper;
import com.msm.admin.modules.system.service.SysDictValueService;
import com.msm.admin.utils.SubjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxy
 * @date 2022/2/11 17:39
 */
@Service
public class CultureServiceImpl extends ServiceImpl<CultureMapper, GwCulture> implements CultureService {

    @Autowired
    private CultureMapper cultureMapper;

    @Autowired
    private SysDictValueMapper sysDictValueMapper;


    @Override
    public Page pageSearch(Page<GwCulture> page, GwCulture culture) {

        QueryWrapper<GwCulture> wrapper=new QueryWrapper<>();

        wrapper= SubjectUtils.isAdmin(wrapper);

        if (culture !=null){
            if (StringUtils.isNotEmpty(culture.getTitle())){
                wrapper.like("title",culture.getTitle());
            }
            if (StringUtils.isNotEmpty(culture.getCategoryId())){
                wrapper.eq("category_id",culture.getCategoryId());
            }
            if (culture.getStatus() !=null){
                if (culture.getStatus() ==20){
                    wrapper.eq("status",0).or().eq("status",2);
                }else {
                    wrapper.eq("status",culture.getStatus());
                }
            }
        }

        wrapper.orderByDesc("create_date");
        Page<GwCulture> gwCulturePage = cultureMapper.selectPage(page, wrapper);
        return gwCulturePage;
    }

    @Override
    public void setStatus(String id, Integer status) {
        GwCulture gwCulture = cultureMapper.selectById(id);
        if (gwCulture !=null){
            gwCulture.setStatus(status);
            updateById(gwCulture);
        }
    }

    @Override
    public Page<GwCulture> pageGwCulture(Page<GwCulture> page, GwCulture gwCulture) {
        QueryWrapper<SysDictValue> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(gwCulture.getStelaClass())){
            wrapper.eq("dict_type_id","3a479546b3c6fea20bb6fd0251ea8efa");
            wrapper.eq("value",gwCulture.getStelaClass());
        }
        SysDictValue sysDictValue = sysDictValueMapper.selectOne(wrapper);

        QueryWrapper<GwCulture> cultureWrapper=new QueryWrapper<>();

        cultureWrapper.eq("stela_class",gwCulture.getStelaClass());
        //按照置顶排序
        cultureWrapper.orderByAsc("sort");

        Page<GwCulture> culturePage = cultureMapper.selectPage(page, cultureWrapper);
        List<GwCulture> cultureList = culturePage.getRecords();
        cultureList.forEach(o -> {
            o.setPcThumb(sysDictValue.getPcThumb());
            o.setMoveThumb(sysDictValue.getMoveThumb());
        });
        return culturePage;
    }
}
