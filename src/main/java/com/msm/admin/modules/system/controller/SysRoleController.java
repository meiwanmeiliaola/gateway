package com.msm.admin.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.Log;
import com.msm.admin.modules.system.entity.SysRole;
import com.msm.admin.modules.system.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description: 角色
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/roles")
public class SysRoleController {
	@Autowired
	private SysRoleService sysRoleService;


    /**
     * 分页列表查询
     */
    @GetMapping
	@RequiresPermissions("system:user:list")
    public IPage list(Page<SysRole> page, SysRole sysRole) {

		return sysRoleService.page(page);
	}


	/**
	  *   添加/编辑
	 * @param sysRole
	 * @return
	 */
	@PutMapping
	@Log("编辑用户")
	@RequiresPermissions("system:user:edit")
    public void save(@RequestBody SysRole sysRole) {
		sysRoleService.saveOrUpdate(sysRole);
    }

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@Log("删除用户")
	@RequiresPermissions("system:user:del")
    public void delete(@PathVariable String id) {
		sysRoleService.removeById(id);
    }

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    public SysRole queryById(@PathVariable String id) {
		SysRole sysRole = sysRoleService.getById(id);
		return sysRole;
    }

    @GetMapping("/tree")
	public List<SysRole> tree() {
		return sysRoleService.list(Wrappers.<SysRole>lambdaQuery().select(SysRole::getId, SysRole::getName));
	}

}
