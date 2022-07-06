package com.msm.admin.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.common.entity.SysFile;

/**
 * @author quavario@gmail.com
 * @date 2019/4/18 15:37
 */
public interface SysFileService extends IService<SysFile> {

    boolean del(String url, String type);
}
