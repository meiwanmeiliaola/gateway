package com.msm.admin.modules.info.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.info.entity.Exhibition;

/**
 * @Description: Exhibition
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface ExhibitionService extends IService<Exhibition> {

    IPage pageSearch(Page<Exhibition> page, Exhibition exhibition);
}
