package com.msm.admin.modules.pub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.pub.service.PubDepartService;
import com.msm.admin.modules.system.entity.SysDepart;
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
@RequestMapping("/v1/departs")
public class PubDepartController {

    @Autowired
    private PubDepartService departService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage list(Page<SysDepart> page, SysDepart depart) {
        return departService.pageSearch(page, depart);
    }



    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public SysDepart queryById(@PathVariable String id) {
        SysDepart depart = departService.getById(id);
        return depart;
    }


}
