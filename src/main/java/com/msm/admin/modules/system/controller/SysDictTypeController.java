package com.msm.admin.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.system.entity.SysDictType;
import com.msm.admin.modules.system.entity.SysDictValue;
import com.msm.admin.modules.system.service.SysDictTypeService;
import com.msm.admin.modules.system.service.SysDictValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Description: 字典类型
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/dictTypes")
public class SysDictTypeController {
	@Autowired
	private SysDictTypeService dictTypeService;

	@Autowired
	private SysDictValueService valueService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage pageSearch(Page<SysDictType> page, SysDictType dictType) {
		return dictTypeService.pageSearch(page, dictType);
	}


	/**
	  *   添加/编辑
	 * @param dictType
	 * @return
	 */
	@PutMapping
    public void save(@RequestBody SysDictType dictType) {
		dictTypeService.saveOrUpdate(dictType);
    }

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
		int size = valueService.list(Wrappers.<SysDictValue>lambdaQuery().eq(SysDictValue::getDictTypeId, id)).size();
		if (size > 0) {
			throw new RuntimeException("不能删除");
		}
		SysDictType dictType = dictTypeService.getById(id);
		dictTypeService.removeById(id);
    }

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    public SysDictType queryById(@PathVariable String id) {
		SysDictType dictType = dictTypeService.getById(id);
		return dictType;
    }

}
