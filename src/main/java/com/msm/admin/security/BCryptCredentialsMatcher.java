package com.msm.admin.security;

import com.msm.admin.modules.system.entity.SysUser;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Bean;

/**
 * @author quavario@gmail.com
 * @date 2020/1/6 15:21
 */
public class BCryptCredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String painPassword = new String(userToken.getPassword());
        return BCrypt.checkpw(painPassword, info.getCredentials().toString());
    }
}
