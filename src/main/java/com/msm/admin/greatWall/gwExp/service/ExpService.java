package com.msm.admin.greatWall.gwExp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.greatWall.gwExp.entity.Exp;


/**
 * @author zxy
 * @date 2022/2/28 11:07
 */
public interface ExpService extends IService<Exp> {
    Page pageSearch(Page<Exp> page, Exp exp);
}
