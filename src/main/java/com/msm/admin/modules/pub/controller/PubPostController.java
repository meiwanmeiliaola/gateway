package com.msm.admin.modules.pub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.View;
import com.msm.admin.modules.info.entity.Post;
import com.msm.admin.modules.pub.service.PubPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quavario@gmail.com
 * @date 2019/12/17 10:13
 */
@RestController
@RequestMapping("/v1/posts")
public class PubPostController {

    @Autowired
    private PubPostService postService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage list(Page<Post> page, Post post) {
        IPage iPage = postService.pageSearch(page, post);
        return iPage;
    }



    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @View(type = "post")
    public Post queryById(@PathVariable String id) {
        Post post = postService.getById(id);
        return post;
    }

}
