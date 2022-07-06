package com.msm.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author quavario@gmail.com
 * @date 2019/8/15 17:47
 */
@Data
public class SysRoleMenu {
    @TableId(type = IdType.UUID)
    private String id;
    private String menuId;
    private String roleId;

    public SysRoleMenu(SysRole sysRole, SysMenu sysMenu) {
        this.menuId = sysMenu.getId();
        this.roleId = sysRole.getId();
    }

    public SysRoleMenu(String roleId, String menuId) {
        this.menuId = menuId;
        this.roleId = roleId;
    }
}
