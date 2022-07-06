package com.msm.admin.modules.pub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.framework.annotation.View;
import com.msm.admin.modules.common.service.impl.RedisService;
import com.msm.admin.modules.pub.service.PubRelicService;
import com.msm.admin.modules.relic.entity.Relic;
import com.msm.admin.modules.system.entity.SysDictValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author quavario@gmail.com
 * @date 2019/12/17 10:13
 */
@RestController
@RequestMapping("/v1/relics")
public class PubRelicController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PubRelicService relicService;

    @Autowired
    Environment env;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage list(Page<Relic> page, Relic relic) {
        return relicService.pageSearch(page, relic);
    }



    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @View(type = "relic")
    public Relic queryById(@PathVariable String id) {
        Relic relic = relicService.getById(id);
        return relic;
    }

    @GetMapping("/infos")
    public Map<String, List<SysDictValue>> infos() {
        return relicService.getInfos();
    }

}
