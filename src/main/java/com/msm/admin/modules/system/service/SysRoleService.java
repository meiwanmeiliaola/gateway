package com.msm.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.system.entity.SysRole;

import java.util.List;

/**
 * @Description: 角色
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
public interface SysRoleService extends IService<SysRole> {
    List<SysRole> listByUser(String userId);
}
