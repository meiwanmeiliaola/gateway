package com.msm.admin.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.Log;
import com.msm.admin.modules.system.entity.SysMenu;
import com.msm.admin.modules.system.entity.SysUser;
import com.msm.admin.modules.system.service.SysMenuService;
import com.msm.admin.utils.SubjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @Description: 菜单
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/menus")
public class SysMenuController {
	@Autowired
	private SysMenuService sysMenuService;

    /**
     * 分页列表查询
     */
    @GetMapping
	@RequiresPermissions("system:menu:list")
    public IPage list(Page<SysMenu> page, SysMenu sysMenu) {
		return sysMenuService.page(page);
	}

	@PostMapping
	public List<SysMenu> listByUser() {
		SysUser currentUser = SubjectUtils.getCurrentUser();
		return sysMenuService.listByUser(currentUser.getId());
	}

	/**
	  *   添加/编辑
	 * @param sysMenu
	 * @return
	 */
	@PutMapping
	@Log("编辑菜单")
	@RequiresPermissions("system:menu:edit")
    public void save(@Valid @RequestBody SysMenu sysMenu) {
		sysMenuService.saveOrUpdate(sysMenu);
    }

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@Log("删除菜单")
	@RequiresPermissions("system:menu:del")
    public void delete(@PathVariable String id) {
		sysMenuService.removeById(id);
    }

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    public SysMenu queryById(@PathVariable String id) {
		SysMenu sysMenu = sysMenuService.getById(id);
		return sysMenu;
    }

    @GetMapping("/tree")
	public List<SysMenu> tree() {
		return sysMenuService.tree();
	}

}
