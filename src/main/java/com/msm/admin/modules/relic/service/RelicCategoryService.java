package com.msm.admin.modules.relic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.relic.entity.RelicCategory;

import java.util.List;

/**
 * @Description: category
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface RelicCategoryService extends IService<RelicCategory> {
    List<RelicCategory> getListByParentId(String parentId);

}
