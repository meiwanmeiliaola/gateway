package com.msm.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.system.entity.SysRole;
import com.msm.admin.modules.system.entity.SysRoleMenu;
import com.msm.admin.modules.system.mapper.SysRoleMapper;
import com.msm.admin.modules.system.service.SysRoleMenuService;
import com.msm.admin.modules.system.service.SysRoleService;
import com.msm.admin.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 角色
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuService roleMenuService;

    @Override
    public SysRole getById(Serializable id) {
        SysRole role = super.getById(id);

        /**
         * 查询当前角色拥有的权限
         */
        List<String> roleMenus = roleMenuService.list(
                Wrappers.<SysRoleMenu>lambdaQuery()
                        .select(SysRoleMenu::getMenuId, SysRoleMenu::getRoleId)
                        .eq(SysRoleMenu::getRoleId, id)
        ).stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());

        role.setMenus(roleMenus);
        return role;
    }

    @Override
    public List<SysRole> listByUser(String userId) {
        List<SysRole> roles = sysRoleMapper.selectListByUser(userId);
        return roles;
    }


    /**
     * 新增或保存角色
     * @param entity
     * @return
     */
    @Override
    public boolean saveOrUpdate(SysRole entity) {
        Assert.isTrue(SubjectUtils.isAdmin(), "");
        boolean isNew = entity.getId() == null;
        // 保存角色 防止角色id为空
        super.saveOrUpdate(entity);

        if (isNew) {
            // 新增角色 直接增加菜单
            roleMenuService.addBatchWithRoleId(entity.getMenus(), entity.getId());
        } else {
            // 更新角色 先删除原有菜单 在增加菜单
            roleMenuService.updateBatchWithRoleId(entity.getMenus(), entity.getId());
        }
        return true;
    }
}
