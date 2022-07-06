package com.msm.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.system.entity.SysRoleMenu;

import java.util.List;

/**
 * @author quavario@gmail.com
 * @date 2019/8/15 14:51
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    void addBatchWithRoleId(List<String> menus, String roleId);

    void updateBatchWithRoleId(List<String> menus, String roleId);

    int removeByRoleId(String roleId);
}
