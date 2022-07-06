package com.msm.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.system.entity.SysUserRole;

import java.util.List;

/**
 * @author quavario@gmail.com
 * @date 2019/8/2 15:59
 */

public interface SysUserRoleService extends IService<SysUserRole> {
    int addBatchWithUserId(List<String> rolesId, String userId);
    int removeByUserId(String userId);
    int updateBatchWithUserId(List<String> rolesId, String userId);

}
