package com.msm.admin.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.Log;
import com.msm.admin.modules.system.entity.SysDepart;
import com.msm.admin.modules.system.service.SysDepartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description: 部门
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/departs")
public class SysDepartController {
	@Autowired
	private SysDepartService sysDepartService;


    /**
     * 分页列表查询
     */
    @GetMapping
	@RequiresPermissions("system:depart:list")
    public IPage list(Page<SysDepart> page, SysDepart sysDepart) {
		return sysDepartService.pageSearch(page, sysDepart);
	}


	/**
	  *   添加/编辑
	 * @param sysDepart
	 * @return
	 */
	@PutMapping
	@Log("编辑部门")
	@RequiresPermissions("system:depart:edit")
    public SysDepart save(@RequestBody SysDepart sysDepart) {
		sysDepartService.saveOrUpdate(sysDepart);
		return sysDepart;
    }

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@Log("删除部门")
	@RequiresPermissions("system:depart:del")
    public void delete(@PathVariable String id) {
		SysDepart sysDepart = sysDepartService.getById(id);
		sysDepartService.removeById(id);
    }

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    public SysDepart queryById(@PathVariable String id) {
		SysDepart sysDepart = sysDepartService.getById(id);
		return sysDepart;
    }

	/**
	 * 列表
	 * @return
	 */
    @GetMapping("/tree")
	public List<SysDepart> tree() {
		return sysDepartService.list(Wrappers.<SysDepart>lambdaQuery().select(SysDepart::getId, SysDepart::getName));
	}

}
