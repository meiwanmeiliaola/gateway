package com.msm.admin.modules.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.info.entity.Post;
import com.msm.admin.modules.info.mapper.PostMapper;
import com.msm.admin.modules.info.service.PostService;
import com.msm.admin.modules.system.service.SysDepartService;
import com.msm.admin.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: post
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    private SysDepartService departService;

    private static final String[] QUERY_LIST_FIELD = {
            "id", "title", "thumb", "dep_id", "type", "create_date", "status"
    };

    @Override
    public List<Post> list(Wrapper<Post> queryWrapper) {
        List<Post> list = super.list(queryWrapper);
        return list;
    }

    @Override
//    @Cacheable(value = "post", keyGenerator = "cacheKeyGenerator", unless = "#result.records.size() == 0")
    public IPage pageSearch(Page<Post> page, Post post) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();

        if (!SubjectUtils.isAdmin()) {
//            queryWrapper.eq("create_by", SubjectUtils.getCurrentUser().getId());
            queryWrapper.eq("dep_id", SubjectUtils.getCurrentUser().getDepId());
        }

        if (!StringUtils.isEmpty(post.getTitle())) {
            queryWrapper.like("title", post.getTitle());
        }

        if (!StringUtils.isEmpty(post.getDepId())) {
            queryWrapper.eq("dep_id", post.getDepId());
        }

        if (!StringUtils.isEmpty(post.getType())) {
            queryWrapper.eq("type", post.getType());
        }

        if (!StringUtils.isEmpty(post.getStatus())) {

            switch (post.getStatus()) {
                // 1 审核通过 首页显示
                case 1:
                    // 2. 审核不通过
                case 2:
                    // 3.已发布
                case 3:
                    // 4. 草稿
                case 4:
                    queryWrapper.eq("status", post.getStatus());
                    break;
                case 23:
                    queryWrapper.eq("status", 2).or().eq("status", 3);
                    break;
                default:
                    break;
            }
        }

        queryWrapper.orderByDesc("status", "update_date");

        queryWrapper.select(QUERY_LIST_FIELD);
        Page<Post> postPage = super.page(page, queryWrapper);
        List<Post> records = postPage.getRecords();

        // 设置年代名称
        records.forEach(item -> {
            // 设置部门名称
            String depName = departService.getNameById(item.getDepId());
            item.setDepName(depName);
        });

        return postPage;
    }

    @Override
    public IPage<Post> reviewSearch(Page<Post> page, Post post) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();

        if (!SubjectUtils.isAdmin()) {
            queryWrapper.eq("create_by", SubjectUtils.getCurrentUser().getId());
        }

        if (post != null) {
            if (!StringUtils.isEmpty(post.getTitle())) {
                queryWrapper.like("title", post.getTitle());
            }
        }

        queryWrapper.eq("status", 2).or().eq("status", 3);


        queryWrapper.orderByAsc("update_by");
        Page<Post> postPage = super.page(page, queryWrapper);

        return postPage;
    }

    /**
     * TODO 更新权限校验
     * @param id
     * @param status
     */
    @Override
    public void setStatus(String id, Integer status) {
        Post post = getById(id);
        if (post != null) {
            post.setStatus(status);
            updateById(post);
        }
    }

    @Override
    @CacheEvict(value = "pubPost", key = "'getById:' + #entity.id", condition = "#entity.id != null")
    public boolean saveOrUpdate(Post entity) {
        if (!SubjectUtils.isAdmin()) {
            if (entity.getId() == null) {
                entity.setStatus(3);
            } else {
                Post dbPost = getById(entity.getId());
                if (dbPost.getStatus() != 1) {
                    entity.setStatus(3);
                }
            }
        }
        return super.saveOrUpdate(entity);
    }

    @Override
//    @Cacheable(value = "post", key = "'getById:' + #id", unless = "#result == null")
    public Post getById(Serializable id) {
        Post post;
        if (!SubjectUtils.isAdmin()) {
            // 防止用户更改非自己发布的内容
            post = getOne(Wrappers.<Post>lambdaQuery().eq(Post::getId, id).eq(Post::getCreateBy, SubjectUtils.getCurrentUser().getId()));
            if (post == null) {
                return null;
            }
        } else {
            post = super.getById(id);
        }
        return post;
    }

    @Override
    @CacheEvict(value = {"pubPost"}, key = "'getById:' + #id")
    public boolean removeById(Serializable id) {
        Boolean removed;
        if (SubjectUtils.isAdmin()) {
            removed = super.removeById(id);
        } else {
            removed = remove(Wrappers.<Post>lambdaQuery().eq(Post::getId, id).eq(Post::getCreateBy, SubjectUtils.getCurrentUser().getId()));
        }
        return removed;
    }
}
