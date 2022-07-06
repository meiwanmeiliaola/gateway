package com.msm.admin.modules.app.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.Log;
import com.msm.admin.modules.app.entity.Ruins;
import com.msm.admin.modules.app.service.RuinsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @Description: 遗址遗迹
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/ruins")
public class RuinsController {
	@Autowired
	private RuinsService ruinsService;

    /**
     * 分页列表查询
     */
    @GetMapping
	@RequiresPermissions("app:ruins:list")
    public IPage list(Page<Ruins> page, Ruins ruins) {
		return ruinsService.pageSearch(page, ruins);
	}


	/**
	  *   添加/编辑
	 * @param ruins
	 * @return
	 */
	@PutMapping
	@Log("历史遗迹编辑/添加")
	@RequiresPermissions("app:ruins:edit")
		public Ruins save(@Valid @RequestBody Ruins ruins) {
			ruinsService.saveOrUpdate(ruins);
			return ruins;
		}

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@Log("历史遗迹删除")
	@RequiresPermissions("app:ruins:del")
    public void delete(@PathVariable String id) {
		Ruins ruins = ruinsService.getById(id);
		ruinsService.removeById(id);
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


	/**
	 * 检索待审核文物
	 */
	@GetMapping("/review")
	@RequiresPermissions("review:ruins")
	public IPage review(Page<Ruins> page, Ruins ruins) {
		return ruinsService.reviewSearch(page, ruins);
	}

	@PutMapping("/review/{id}/{status}")
	@Log("历史遗迹审核")
	@RequiresPermissions("review:ruins")
	public void status(@PathVariable String id, @PathVariable Integer status) {
		ruinsService.setStatus(id, status);
	}

}
