package com.msm.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.system.entity.SysMenu;

import java.util.List;

/**
 * @Description: menu
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> listByUser(String userId);
    List<SysMenu> listByRole(String roleId);
    List<String> listPermissionByUser(String userId);
    List<SysMenu> tree();
}
