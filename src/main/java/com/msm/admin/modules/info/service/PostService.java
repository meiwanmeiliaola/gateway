package com.msm.admin.modules.info.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.info.entity.Post;
import com.msm.admin.modules.relic.entity.Relic;

/**
 * @Description: area
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface PostService extends IService<Post> {

    IPage pageSearch(Page<Post> page, Post post);

    IPage reviewSearch(Page<Post> page, Post post);

    void setStatus(String id, Integer status);
}
