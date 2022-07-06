package com.msm.admin.modules.relic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.relic.entity.Relic;
import com.msm.admin.modules.relic.entity.RelicCategory;
import com.msm.admin.modules.system.entity.SysDictValue;

import java.util.List;
import java.util.Map;

/**
 * @Description: area
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface RelicService extends IService<Relic> {
    IPage<Relic> pageSearch(Page<Relic> page, Relic relic);

    Map<String, List> getInfo();

    // 待审核文物检索
    IPage reviewSearch(Page<Relic> page, Relic relic);

    void setStatus(String id, Integer status);

    IPage pageTest(Page<Relic> page, Relic relic);
}
