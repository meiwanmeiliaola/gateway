package com.msm.admin.modules.app.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.Log;
import com.msm.admin.modules.app.entity.Explore;
import com.msm.admin.modules.app.service.ExploreService;
//import com.msm.admin.modules.common.service.impl.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @Description: 探索发现
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/explores")
public class ExploreController {
	@Autowired
	private ExploreService exploreService;

//	@Autowired
//	private RedisService redisService;

    /**
     * 分页列表查询
     */
    @GetMapping
	@RequiresPermissions("app:explore:list")
    public IPage list(Page<Explore> page, Explore explore) {
		return exploreService.pageSearch(page, explore);
	}


	/**
	  *   添加/编辑
	 * @param explore
	 * @return
	 */
	@PutMapping
	@Log("探索发现编辑/添加")
	@RequiresPermissions("app:explore:edit")
	public Explore save(@Valid @RequestBody Explore explore) {
		exploreService.saveOrUpdate(explore);
		return explore;
    }

    /**
	 * 检索待审核文物
	 */
	@GetMapping("/review")
	@RequiresPermissions("review:explore")
	public IPage review(Page<Explore> page, Explore explore) {
		return exploreService.reviewSearch(page, explore);
	}


	@PutMapping("/review/{id}/{status}")
	@Log("审核探索发现")
	@RequiresPermissions("review:explore")
	public void status(@PathVariable String id, @PathVariable Integer status) {
		exploreService.setStatus(id, status);
	}

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@Log("探索发现删除")
	@RequiresPermissions("app:explore:del")
    public void delete(@PathVariable String id) {
		Explore explore = exploreService.getById(id);
		exploreService.removeById(id);
    }

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    public Explore queryById(@PathVariable String id) {
		Explore explore = exploreService.getById(id);
		return explore;
    }

}
