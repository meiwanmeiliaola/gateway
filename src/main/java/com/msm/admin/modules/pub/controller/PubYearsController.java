package com.msm.admin.modules.pub.controller;

import com.msm.admin.modules.common.entity.Years;
import com.msm.admin.modules.pub.service.PubYearsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author quavario@gmail.com
 * @date 2019/12/17 10:13
 */
@RestController
@RequestMapping("/v1/years")
public class PubYearsController {

    @Autowired
    private PubYearsService yearsService;

    /**
     * 分页列表查询
     */
    @GetMapping("/tree")
    public List<Years> tree() {
        return yearsService.tree();
    }



    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Years queryById(@PathVariable String id) {
        Years years = yearsService.getById(id);
        return years;
    }

    @GetMapping("/list")
    public List<Years> list() {
        return yearsService.list();
    }
}
