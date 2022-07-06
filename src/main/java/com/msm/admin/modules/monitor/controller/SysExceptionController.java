package com.msm.admin.modules.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.monitor.entity.SysException;
import com.msm.admin.modules.monitor.service.SysExceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import com.msm.admin.modules.common.service.impl.RedisService;


/**
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/exceptions")
public class SysExceptionController {
	
	@Autowired
	private SysExceptionService exceptionService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage pageSearch(Page<SysException> page, SysException exception) {
		return exceptionService.pageSearch(page, exception);
	}

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    public SysException queryById(@PathVariable String id) {
		SysException exception = exceptionService.getById(id);
		return exception;
    }



}
