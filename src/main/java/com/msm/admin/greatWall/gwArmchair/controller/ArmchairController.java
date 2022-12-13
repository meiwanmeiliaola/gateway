package com.msm.admin.greatWall.gwArmchair.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.Log;
import com.msm.admin.greatWall.gwArmchair.entity.Armchair;
import com.msm.admin.greatWall.gwArmchair.service.ArmchairService;
import com.msm.admin.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("armchair")
public class ArmchairController {


    @Resource
    private ArmchairService armchairService;

    @PutMapping
    @Log("编辑纸上谈兵")
    @RequiresPermissions("relic:armchair:edit")
    public Armchair save(@RequestBody Armchair armchair){

        if (armchair.getId() ==null){
            armchair.setStatus(0);
        }else if (armchair.getStatus() ==2){
            armchair.setStatus(0);
        }

        if(StringUtils.isNotEmpty(armchair.getCreateDate())){
            armchair.setUpdateDate(DateUtil.getNowTime());
        }else {
            //获取当前时间
            armchair.setCreateDate(DateUtil.getNowTime());
        }

        armchairService.saveOrUpdate(armchair);
        return armchair;
    }

    @DeleteMapping("{id}")
    @Log("删除纸上谈兵")
    @RequiresPermissions("relic:armchair:delete")
    public void delete(@PathVariable String id){
        armchairService.removeById(id);
    }

    @GetMapping("{id}")
    public Armchair getById(@PathVariable String id){
        return armchairService.getById(id);
    }

    @GetMapping("list")
    @Log("查询纸上谈兵")
    @RequiresPermissions("relic:armchair:list")
    public IPage<Armchair> list(Page<Armchair> page,Armchair armchair){
        return armchairService.pageSearch(page,armchair);
    }

    @GetMapping("query")
    public List<Armchair> query(Armchair armchair){
        QueryWrapper<Armchair> wrapper=new QueryWrapper<>();
        wrapper.eq("status",1);
        return armchairService.list(wrapper);
    }

    @GetMapping("review/{id}/{status}")
    public void review(@PathVariable String id,@PathVariable Integer status){
        Armchair armchair=new Armchair();
        armchair.setStatus(status).setId(id);
        armchairService.updateById(armchair);
    }


}
