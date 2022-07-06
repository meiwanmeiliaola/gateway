package com.msm.admin.modules.pub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.info.entity.Celebrity;
import com.msm.admin.modules.info.service.CelebrityService;
import com.msm.admin.modules.pub.service.PubCelebrityService;
import com.msm.admin.modules.system.entity.SysDictValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author quavario@gmail.com
 * @date 2019/12/17 10:13
 */
@RestController
@RequestMapping("/v1/celebrities")
public class PubCelebrityController {

    @Autowired
    private PubCelebrityService celebrityService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage list(Page<Celebrity> page, Celebrity celebrity) {
        IPage iPage = celebrityService.pageSearch(page, celebrity);
        return iPage;
    }



    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Celebrity queryById(@PathVariable String id) {
        Celebrity celebrity = celebrityService.getById(id);
        return celebrity;
    }

}
