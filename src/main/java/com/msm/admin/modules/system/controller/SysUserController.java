package com.msm.admin.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.Log;
import com.msm.admin.modules.common.service.impl.RedisService;
import com.msm.admin.modules.system.entity.SysUser;
import com.msm.admin.modules.system.service.SysUserService;
import com.msm.admin.utils.SubjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @Description: 用户
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private RedisService redisService;

    /**
     * 分页列表查询
     */
    @GetMapping
	@RequiresPermissions("system:user:list")
    public IPage pageSearch(Page<SysUser> page, SysUser sysUser) {

		return sysUserService.pageSearch(page, sysUser);
	}

	@GetMapping("/list")
	@RequiresPermissions("system:user:list")
	public List<SysUser> list() {
    	return sysUserService.list(Wrappers.<SysUser>lambdaQuery().select(SysUser::getId, SysUser::getName));
	}

	/**
	  *   添加/编辑
	 * @param sysUser
	 * @return
	 */
	@PutMapping
	@Log("编辑用户")
	@RequiresPermissions("system:user:edit")
    public void save(@Valid @RequestBody SysUser sysUser) {
		sysUserService.saveOrUpdate(sysUser);
    }

    @PostMapping("/fz/{id}")
	@Log("冻结用户")
	@RequiresPermissions("system:user:edit")
	public String freezeUser(@PathVariable String id) {
		return sysUserService.freezeUser(id);
	}

	@PostMapping("/unFz/{id}")
	@Log("解冻用户")
	@RequiresPermissions("system:user:edit")
	public String unFreezeUser(@PathVariable String id) {
		return sysUserService.unFreezeUser(id);
	}

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@Log("删除用户")
	@RequiresPermissions("system:user:del")
    public void delete(@PathVariable String id) {
		sysUserService.removeById(id);
    }

    @PostMapping("/resetPw")
	public void resetPw(String pw, @NotEmpty String newPw, @NotEmpty String comfirmPw) {
		sysUserService.resetPw(pw, newPw, comfirmPw);
	}

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    public SysUser queryById(@PathVariable String id) {
		SysUser sysUser = sysUserService.getById(id);
		return sysUser;
    }

    @RequestMapping("/info")
	public Map userInfo(HttpSession session) {
		System.out.println(session.getId());
		Map map = sysUserService.userInfo();
		return map;
	}

//	@RequestMapping("/")
}
