package com.msm.admin.modules.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.common.entity.TreeData;
import com.msm.admin.modules.common.entity.Years;
import com.msm.admin.modules.common.service.YearsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Description: 文物
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/years")
public class YearsController {
	@Autowired
	private YearsService yearsService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage list(Page<Years> page, Years years) {
		return yearsService.page(page);
	}


	/**
	  *   添加/编辑
	 * @param years
	 * @return
	 */
	@PutMapping
    public Years save(@RequestBody Years years) {
		yearsService.saveOrUpdate(years);
		return years;
    }

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
		Years years = yearsService.getById(id);
		yearsService.removeById(id);
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

	@GetMapping("/tree")
	public List<Years> tree() {
		List<Years> list = yearsService.list();
		List<Years> years = TreeData.toTreeList(list).stream().sorted(Comparator.comparing(Years::getSort)).collect(Collectors.toList());
		return years;
	}

	@GetMapping("/list")
	public List<Years> list() {
		List<Years> list = yearsService.list(
				Wrappers.<Years>lambdaQuery().eq(Years::getType, 2)
		);
		return list;
	}

}
