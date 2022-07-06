package com.msm.admin.modules.info.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.info.entity.Celebrity;

/**
 * @Description: Celebrity
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface CelebrityService extends IService<Celebrity> {

    IPage pageSearch(Page<Celebrity> page, Celebrity celebrity);

    IPage reviewSearch(Page<Celebrity> page, Celebrity celebrity);

    void setStatus(String id, Integer status);

    /**
     * 历史人物校验
     * @param celebrity 历史人物对象
     * @return 校验结果
     */
    String check(Celebrity celebrity);
}
