package com.msm.admin.modules.pub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.pub.service.PubDictValueService;
import com.msm.admin.modules.pub.service.PubRelicService;
import com.msm.admin.modules.relic.entity.Relic;
import com.msm.admin.modules.system.entity.SysDictValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quavario@gmail.com
 * @date 2019/12/19 14:39
 */
public class PubDictValueController {
    @Autowired
    private PubDictValueService dictValueService;


}
