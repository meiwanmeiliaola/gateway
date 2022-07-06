package com.msm.admin.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.system.entity.SysDictValue;
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
@RequestMapping("/dictValues")
public class SysDictValueController {
	@Autowired
	private SysDictValueService dictValueService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage list(Page<SysDictValue> page, SysDictValue dictValue) {
		return dictValueService.pageSearch(page, dictValue);
	}


	/**
	  *   添加/编辑
	 * @param dictValue
	 * @return
	 */
	@PutMapping
    public void save(@RequestBody SysDictValue dictValue) {
		dictValueService.saveOrUpdate(dictValue);
    }

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
		SysDictValue dictValue = dictValueService.getById(id);
		dictValueService.removeById(id);
    }

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    public SysDictValue queryById(@PathVariable String id) {
		SysDictValue dictValue = dictValueService.getById(id);
		return dictValue;
    }

}
