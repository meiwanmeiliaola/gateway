package com.msm.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author quavario@gmail.com
 * @date 2019/8/2 15:53
 */
@Data
public class SysUserRole {
    @TableId(type = IdType.UUID)
    private String id;
    private String userId;
    private String roleId;

    public SysUserRole() {
    }


    public SysUserRole(SysUser user, SysRole sysRole) {
        this.roleId = sysRole.getId();
        this.userId = user.getId();
    }

    public SysUserRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
