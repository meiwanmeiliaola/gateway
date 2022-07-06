package com.msm.admin.modules.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.common.entity.Advice;
import com.msm.admin.modules.common.service.AdviceService;
import com.msm.admin.modules.common.service.AdviceService;
import com.msm.admin.utils.SubjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * @Description: 文物
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/advices")
public class AdviceController {
	@Autowired
	private AdviceService adviceService;
	/**
	  *   添加/编辑
	 * @param advice
	 * @return
	 */
	@PutMapping
    public Advice save(@RequestBody Advice advice) {
		advice.setSendDate(new Date());

		String currentUserId = SubjectUtils.getCurrentUser().getId();
		advice.setSender(currentUserId);

		adviceService.saveOrUpdate(advice);
		return advice;
    }

	/**
	  * itemId
	 * @param itemId
	 * @return
	 */
	@GetMapping("/{itemId}")
    public Advice queryById(@PathVariable String itemId) {
		List<Advice> list = adviceService.list(Wrappers.<Advice>lambdaQuery().eq(Advice::getItemId, itemId).orderByDesc(Advice::getSendDate));
		return list.size() > 0 ? list.get(0) : null;
    }


}
