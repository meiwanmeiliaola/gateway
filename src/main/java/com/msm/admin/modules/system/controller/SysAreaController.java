package com.msm.admin.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.system.entity.SysArea;
import com.msm.admin.modules.system.service.SysAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description: 文物
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/areas")
public class SysAreaController {
	@Autowired
	private SysAreaService areaService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage list(Page<SysArea> page, SysArea area) {
		return areaService.page(page);
	}


	/**
	  *   添加/编辑
	 * @param area
	 * @return
	 */
	@PutMapping
    public void save(@RequestBody SysArea area) {
		areaService.saveOrUpdate(area);
    }

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
		SysArea area = areaService.getById(id);
		areaService.removeById(id);
    }

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    public SysArea queryById(@PathVariable String id) {
		SysArea area = areaService.getById(id);
		return area;
    }

    @GetMapping("/list")
	public List<SysArea> list() {
		List<SysArea> list = areaService.list(
				Wrappers.<SysArea>lambdaQuery().select(SysArea::getId, SysArea::getName)
						.eq(SysArea::getParentId, "1")
		);
		return list;
	}

}
