package com.msm.admin.modules.relic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.common.entity.TreeData;
import com.msm.admin.modules.relic.entity.RelicCategory;
import com.msm.admin.modules.relic.service.RelicCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description: 文物类别
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/relicCategories")
public class RelicCategoryController {
	@Autowired
	private RelicCategoryService relicCategoryService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage page(Page<RelicCategory> page, RelicCategory relicCategory) {
		return relicCategoryService.page(page);
	}


	/**
	  *   添加/编辑
	 * @param relicCategory
	 * @return
	 */
	@PutMapping
    public void save(@RequestBody RelicCategory relicCategory) {
		relicCategoryService.saveOrUpdate(relicCategory);
    }

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
		RelicCategory relicCategory = relicCategoryService.getById(id);
		relicCategoryService.removeById(id);
    }

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    public RelicCategory queryById(@PathVariable String id) {
		RelicCategory relicCategory = relicCategoryService.getById(id);
		return relicCategory;
    }

	@GetMapping("/list")
	public List<RelicCategory> list() {
		List<RelicCategory> list = relicCategoryService.list();
		List<RelicCategory> relicCategories = TreeData.toTreeList(list);
		return relicCategories;
	}

}
