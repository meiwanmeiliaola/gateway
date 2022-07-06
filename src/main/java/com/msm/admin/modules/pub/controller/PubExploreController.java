package com.msm.admin.modules.pub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.View;
import com.msm.admin.modules.app.entity.Explore;
import com.msm.admin.modules.pub.service.PubExploreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quavario@gmail.com
 * @date 2019/12/17 10:13
 */
@RestController
@RequestMapping("/v1/explores")
public class PubExploreController {

    @Autowired
    private PubExploreService exploreService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage list(Page<Explore> page, Explore explore) {
        return exploreService.pageSearch(page, explore);
    }



    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @View(type = "explore")
    public Explore queryById(@PathVariable String id) {
        Explore explore = exploreService.getById(id);
        return explore;
    }

}
