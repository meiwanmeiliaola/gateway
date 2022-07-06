package com.msm.admin.modules.info.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.Log;
import com.msm.admin.modules.info.entity.Post;
import com.msm.admin.modules.info.service.PostService;
import com.msm.admin.modules.relic.entity.Relic;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @Description: 文物
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {
	@Autowired
	private PostService postService;

    /**
     * 分页列表查询
     */
    @GetMapping
	@RequiresPermissions("info:post:list")
	public IPage list(Page<Post> page, Post post) {
		return postService.pageSearch(page, post);
	}


	/**
	 * 检索待审核文物
	 */
	@GetMapping("/review")
	@RequiresPermissions("review:post")
	public IPage review(Page<Post> page, Post post) {
		return postService.reviewSearch(page, post);
	}

	@PutMapping("/review/{id}/{status}")
	@Log("审核文章")
	@RequiresPermissions("review:post")
	public void status(@PathVariable String id, @PathVariable Integer status) {
		postService.setStatus(id, status);
	}


	/**
	  *   添加/编辑
	 * @param post
	 * @return
	 */
	@PutMapping
	@Log("编辑文章")
	@RequiresPermissions("info:post:edit")
	public Post save(@Valid @RequestBody Post post) {
		postService.saveOrUpdate(post);
		return post;
    }

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@Log("删除文章")
	@DeleteMapping("/{id}")
	@RequiresPermissions("info:post:del")
	public void delete(@PathVariable String id) {
		Post post = postService.getById(id);
		postService.removeById(id);
    }

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    public Post queryById(@PathVariable String id) {
		Post post = postService.getById(id);
		return post;
    }

}
