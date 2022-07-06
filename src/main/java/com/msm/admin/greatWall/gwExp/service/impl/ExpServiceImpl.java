package com.msm.admin.greatWall.gwExp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.greatWall.gwExp.entity.Exp;
import com.msm.admin.greatWall.gwExp.mapper.ExpMapper;
import com.msm.admin.greatWall.gwExp.service.ExpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zxy
 * @date 2022/2/28 11:07
 */
@Service
public class ExpServiceImpl extends ServiceImpl<ExpMapper, Exp> implements ExpService {

    @Autowired
    private ExpMapper expMapper;


    @Override
    public Page pageSearch(Page<Exp> page, Exp exp) {
        QueryWrapper<Exp> wrapper=new QueryWrapper<>();
        if (exp !=null){
            if (StringUtils.isNotEmpty(exp.getTitle())){
                wrapper.like("title",exp.getTitle());
            }
        }
        wrapper.orderByDesc("create_date");
        Page<Exp> gwExpIPage = expMapper.selectPage(page, wrapper);
        return gwExpIPage;
    }
}
