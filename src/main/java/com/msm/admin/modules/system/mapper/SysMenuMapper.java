package com.msm.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msm.admin.modules.system.entity.SysMenu;

import java.util.List;

/**
 * @Description: menu
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据角色ID查询顶级菜单
     * @param roleId 角色ID
     * @return 角色拥有的菜单
     */
    List<SysMenu> selectListByRole(String roleId);

    /**
     * 根据用户id查询用户菜单列表（非属性结构）
     * @param userId 用户ID
     * @return 用户拥有的菜单列表
     */
    List<SysMenu> selectListByUser(String userId);

    List<String> selectPermissionListByUser(String userId);

    List<SysMenu> selectChildById(String id);

    List<SysMenu> selectParentById(String id);
}
