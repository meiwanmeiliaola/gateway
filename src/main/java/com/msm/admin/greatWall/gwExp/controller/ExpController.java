package com.msm.admin.greatWall.gwExp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.greatWall.gwExp.entity.Exp;
import com.msm.admin.greatWall.gwExp.service.ExpService;
import com.msm.admin.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zxy
 * @date 2022/2/28 11:06
 */
@RestController
@RequestMapping(value = "gwExp")
public class ExpController {

    @Autowired
    private ExpService service;

    @PutMapping
    public Exp save(@RequestBody Exp exp){
        if (StringUtils.isEmpty(exp.getCreateDate())){
            exp.setCreateDate(DateUtil.getNowTime());
        }else {
            exp.setUpdateDate(DateUtil.getNowTime());
        }
        service.saveOrUpdate(exp);
        return exp;
    }


    @DeleteMapping(value ="{id}")
    public void delete(@PathVariable String id){
        service.removeById(id);
    }

    @GetMapping(value = "list")
    public Page page(Page<Exp> page, Exp exp){
       return service.pageSearch(page,exp);
    }

    @GetMapping(value = "{id}")
    public Exp getById(@PathVariable String id){
        Exp exp = service.getById(id);
        return exp;
    }

}
