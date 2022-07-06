package com.msm.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.system.entity.SysMenu;
import com.msm.admin.modules.system.entity.SysUser;
import com.msm.admin.modules.common.entity.TreeData;
import com.msm.admin.modules.system.mapper.SysMenuMapper;
import com.msm.admin.modules.system.service.SysMenuService;
import com.msm.admin.modules.system.service.SysUserService;
import com.msm.admin.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: menu
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    @Lazy
    private SysUserService userService;


    @Autowired
    private SysMenuMapper menuMapper;

    /**
     * 根据用户ID查询用户菜单
     * @param userId 用户菜单
     * @return 当前用户的菜单节点
     */
    @Override
    public List<SysMenu> listByUser(String userId) {
        List<SysMenu> result = new ArrayList<>();
        //查询用户
        SysUser user = userService.getById(userId);
        //用户不存在返回空数组
        Assert.notNull(user, "current user is not exist");

        if (user.admin()) {
            return list();
        }
        List<SysMenu> list = menuMapper.selectListByUser(userId);

        list.forEach(item -> {
            List<SysMenu> childList = menuMapper.selectChildById(item.getId());
            List<SysMenu> parentList = menuMapper.selectParentById(item.getId());
            result.addAll(childList);
            result.addAll(parentList);
        });

        // 去重
        HashSet<SysMenu> resultSet = new HashSet<>();
        resultSet.addAll(result);

        List<SysMenu> menus = resultSet.stream().sorted(Comparator.comparing(SysMenu::getSort)).collect(Collectors.toList());

        // 排序
        /*result = TreeData.toTreeList(list).stream()
                .sorted(Comparator.comparing(SysMenu::getSort))
                // 过滤无用父级菜单
                .filter(item -> !("0".equals(item.getParentId()) && CollectionUtils.isEmpty(item.getChildren())))
                .collect(Collectors.toList());*/

        /*ArrayList<SysMenu> menus = new ArrayList<>();
        menus.addAll(resultSet);*/
        return TreeData.toTreeList(menus);
    }

    @Override
    public List<SysMenu> listByRole(String roleId) {
        return menuMapper.selectListByRole(roleId);
    }

    @Override
    public List<String> listPermissionByUser(String userId) {
        return menuMapper.selectPermissionListByUser(userId);
    }

    @Override
    public List<SysMenu> tree() {
        List<SysMenu> list = super.list(
                Wrappers.<SysMenu>lambdaQuery().orderByAsc(SysMenu::getSort)
                .select(SysMenu::getId, SysMenu::getText, SysMenu::getParentId)
        );
        List<SysMenu> treeMenus = TreeData.toTreeList(list);
        return treeMenus;
    }

    @Override
    public boolean saveOrUpdate(SysMenu entity) {
        if (StringUtils.isEmpty(entity.getParentId())) {
            entity.setParentId("0");
        }
        return super.saveOrUpdate(entity);
    }



    @Override
    public SysMenu getById(Serializable id) {
        SysMenu menu;
        if (!SubjectUtils.isAdmin()) {
            menu = getOne(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getId, id).eq(SysMenu::getCreateBy, SubjectUtils.getCurrentUser().getId()));
        } else {
            menu = super.getById(id);
        }

        return menu;
    }

    @Override
    public boolean removeById(Serializable id) {
        Boolean removed;
        if (SubjectUtils.isAdmin()) {
            removed = super.removeById(id);
        } else {
            removed = remove(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getId, id).eq(SysMenu::getCreateBy, SubjectUtils.getCurrentUser().getId()));
        }
        return removed;
    }

}
