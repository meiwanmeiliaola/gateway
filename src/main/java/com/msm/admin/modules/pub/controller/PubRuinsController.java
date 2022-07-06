package com.msm.admin.modules.pub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.app.entity.Ruins;
import com.msm.admin.modules.pub.service.PubRuinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author quavario@gmail.com
 * @date 2019/12/17 10:13
 */
@RestController
@RequestMapping("/v1/ruins")
public class PubRuinsController {

    @Autowired
    private PubRuinsService ruinsService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage list(Page<Ruins> page, Ruins ruins) {
        return ruinsService.pageSearch(page, ruins);
    }



    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Ruins queryById(@PathVariable String id) {
        Ruins ruins = ruinsService.getById(id);
        return ruins;
    }

}
