package com.msm.admin.modules.system.controller;

import com.msm.admin.common.enums.ErrorInfo;
import com.msm.admin.framework.annotation.Log;
import com.msm.admin.framework.exception.BaseException;
import com.msm.admin.modules.system.entity.SysUser;
import com.msm.admin.modules.system.mapper.SysMenuMapper;
import com.msm.admin.security.RetryUserBlocker;
import com.msm.admin.utils.ExceptionThrower;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author quavario@gmail.com
 * @date 2019/12/11 17:14
 */
@RestController
@RequestMapping("/auth")
@Log4j
public class AuthController {

    @Autowired
    RetryUserBlocker retryUserBlocker;

    @Autowired
    private SysMenuMapper menuMapper;

    @Log(value = "登录操作", type = 1)
    @RequestMapping("/login")
    SysUser login(String username, String password, boolean remember) {

        Subject subject = SecurityUtils.getSubject();

        boolean b = retryUserBlocker.testIpAvailable();
        if (!b) {
            ExceptionThrower.of(ErrorInfo.IP_LOCKED);
        }

//        UsernamePasswordToken token = new UsernamePasswordToken(username, password, remember);
//        subject.login(token);
//        SysUser sysUser = (SysUser) subject.getPrincipal();
//        sysUser.setPassword(null);

        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, remember);
            subject.login(token);
            SysUser sysUser = (SysUser) subject.getPrincipal();
            // 冻结检测
            if (sysUser.getStatus() == 2) {
                ExceptionThrower.of(ErrorInfo.ACCOUNT_LOCKED);
            }
            sysUser.setPassword(null);
            return sysUser;
        } catch (AuthenticationException e) {
            retryUserBlocker.loginFail(username);
            ExceptionThrower.of(ErrorInfo.UNKNOWN_USER);
        }
        return new SysUser();

    }

    @RequestMapping("/logout")
    @Log(value = "退出", type = 1)
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "ok";
    }

/*    *//**
     * 测试漏洞
     *
     * @return
     *//*
    @GetMapping("testLog")
    public void testLog(@RequestParam String testLog){
        log.info("testLog:"+testLog);
    }*/
}
