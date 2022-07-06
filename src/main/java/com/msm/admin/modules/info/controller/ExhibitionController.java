package com.msm.admin.modules.info.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.Log;
import com.msm.admin.modules.info.entity.Exhibition;
import com.msm.admin.modules.info.entity.Exhibition;
import com.msm.admin.modules.info.service.ExhibitionService;
import com.msm.admin.modules.info.service.ExhibitionService;
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
@RequestMapping("/exhibitions")
public class ExhibitionController {
	@Autowired
	private ExhibitionService exhibitionService;

	/**
	 * 分页列表查询
	 */
	@GetMapping
	@RequiresPermissions("info:exhibition:list")
	public IPage list(Page<Exhibition> page, Exhibition exhibition) {
		return exhibitionService.pageSearch(page, exhibition);
	}


	/**
	 * 检索待审核文物
	 */
	@GetMapping("/review")
	@RequiresPermissions("review:exhibition")
	public IPage review(Page<Exhibition> page, Exhibition exhibition) {
//		return exhibitionService.reviewSearch(page, exhibition);
        return null;
	}

	@PutMapping("/review/{id}/{status}")
	@Log("审核展览")
	@RequiresPermissions("review:exhibition")
	public void status(@PathVariable String id, @PathVariable Integer status) {
//		exhibitionService.setStatus(id, status);
	}


	/**
	 *   添加/编辑
	 * @param exhibition
	 * @return
	 */
	@PutMapping
	@Log("编辑展览")
	@RequiresPermissions("info:exhibition:edit")
	public Exhibition save(@Valid @RequestBody Exhibition exhibition) {
		exhibitionService.saveOrUpdate(exhibition);
		return exhibition;
	}

	/**
	 *   通过id删除
	 * @param id
	 * @return
	 */
	@Log("删除展览")
	@DeleteMapping("/{id}")
	@RequiresPermissions("info:exhibition:del")
	public void delete(@PathVariable String id) {
		Exhibition exhibition = exhibitionService.getById(id);
		exhibitionService.removeById(id);
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Exhibition queryById(@PathVariable String id) {
		Exhibition exhibition = exhibitionService.getById(id);
		return exhibition;
	}

}
