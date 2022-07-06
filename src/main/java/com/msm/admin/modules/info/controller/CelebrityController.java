package com.msm.admin.modules.info.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.Log;
import com.msm.admin.modules.common.entity.ResultBean;
import com.msm.admin.modules.info.entity.Celebrity;
import com.msm.admin.modules.info.service.CelebrityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @Description: 历史人物
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/celebrities")
public class CelebrityController {
	@Autowired
	private CelebrityService celebrityService;

    /**
     * 分页列表查询
     */
    @GetMapping
	@RequiresPermissions("info:celebrity:list")
    public IPage list(Page<Celebrity> page, Celebrity celebrity) {
		return celebrityService.pageSearch(page, celebrity);
	}


	/**
	  *   添加/编辑
	 * @param celebrity
	 * @return
	 */
	@PutMapping
	@Log("编辑历史人物")
	@RequiresPermissions("info:celebrity:edit")
	public Celebrity save(@Valid @RequestBody Celebrity celebrity) {
		celebrityService.saveOrUpdate(celebrity);
		return celebrity;
    }

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@Log("删除历史人物")
	@RequiresPermissions("info:celebrity:del")
	public void delete(@PathVariable String id) {
		Celebrity celebrity = celebrityService.getById(id);
		celebrityService.removeById(id);
    }

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Celebrity queryById(@PathVariable String id) {
		Celebrity celebrity = celebrityService.getById(id);
		return celebrity;
    }

    @GetMapping("/check")
	public String check(Celebrity celebrity) {
		return celebrityService.check(celebrity);
//		ResultBean<String> stringResultBean = new ResultBean<>();
//		stringResultBean.setData(check);
//		return stringResultBean;
	}


}
