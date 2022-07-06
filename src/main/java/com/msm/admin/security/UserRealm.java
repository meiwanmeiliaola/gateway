package com.msm.admin.security;

import com.msm.admin.common.enums.ErrorInfo;
import com.msm.admin.modules.system.entity.SysUser;
import com.msm.admin.modules.system.service.SysMenuService;
import com.msm.admin.modules.system.service.SysUserService;
import com.msm.admin.utils.ExceptionThrower;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

/**
 * @author quavario@gmail.com
 * @date 2019/12/11 17:18
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    private SysUserService sysUserService;
    private SysMenuService menuService;
    public UserRealm(SysUserService sysUserService, SysMenuService menuService) {
        this.sysUserService = sysUserService;
        this.menuService = menuService;
    }

    /**
     * 授权
     * @param principalCollection 身份列表
     * @return 权限列表
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        List<String> permissions = menuService.listPermissionByUser(user.getId());
//        List<String> permissions = null;

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 超级管理员拥有所有权限
     * @param principals 角色
     * @param permission 权限
     * @return 是否允许执行
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        return user.admin() || super.isPermitted(principals, permission);
    }

    /**
     * 认证
     * @param authenticationToken 令牌
     * @return 认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        SysUser sysUser = sysUserService.getByUsername(token.getUsername());
        if (sysUser == null) {
            ExceptionThrower.of(ErrorInfo.UNKNOWN_USER);
        }

        return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), null, getName());
    }
}
