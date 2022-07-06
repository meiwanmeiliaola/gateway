package com.msm.admin.modules.relic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.Log;
import com.msm.admin.modules.relic.entity.Relic;
import com.msm.admin.modules.relic.service.RelicService;
import com.msm.admin.modules.system.entity.SysDictValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * @Description: 文物
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/relics")
public class RelicController {
	@Autowired
	private RelicService relicService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage pageSearch(Page<Relic> page, Relic relic) {
		return relicService.pageSearch(page, relic);
	}

	@GetMapping("/test")
	public IPage test(Page<Relic> page, Relic relic){
    	return relicService.pageTest(page,relic);
	}

	/**
	 * 检索待审核文物
	 */
	@GetMapping("/review")
	@RequiresPermissions("review:relic")
	public IPage review(Page<Relic> page, Relic relic) {
		return relicService.reviewSearch(page, relic);
	}

	@PutMapping("/review/{id}/{status}")
	@Log("审核文物")
	@RequiresPermissions("review:relic")
	public void status(@PathVariable String id, @PathVariable Integer status) {
		relicService.setStatus(id, status);
	}


	/**
	  *   添加/编辑
	 * @param relic
	 * @return
	 */
	@PutMapping
	@Log("保存文物")
    public Relic save(@Valid @RequestBody Relic relic) {
		relicService.saveOrUpdate(relic);
		return relic;
    }

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@Log("删除文物")
	public void delete(@PathVariable String id) {
		Relic relic = relicService.getById(id);
		relicService.removeById(id);
    }

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    public Relic queryById(@PathVariable String id) {
		Relic relic = relicService.getById(id);
		return relic;
    }

    @GetMapping("/info")
	public Map<String, List> relicInfo() {
		return relicService.getInfo();
	}

}
