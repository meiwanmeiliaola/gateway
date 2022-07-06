package com.msm.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.system.entity.SysRoleMenu;
import com.msm.admin.modules.system.mapper.SysRoleMenuMapper;
import com.msm.admin.modules.system.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author quavario@gmail.com
 * @date 2019/10/24 17:56
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public void addBatchWithRoleId(List<String> menus, String roleId) {
        // 去重
        menus = menus.stream().distinct().collect(Collectors.toList());
        Assert.notEmpty(menus, "rolesId为空");
        roleMenuMapper.insertBatchWithRoleId(menus, roleId);
    }

    @Override
    public void updateBatchWithRoleId(List<String> menus, String roleId) {
        removeByRoleId(roleId);
        addBatchWithRoleId(menus, roleId);
    }

    @Override
    public int removeByRoleId(String roleId) {
        super.remove(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, roleId));
        return 1;
    }
}
