package com.msm.admin.security;

import com.msm.admin.common.enums.ErrorInfo;
import com.msm.admin.modules.common.entity.IpUtils;
import com.msm.admin.modules.common.service.impl.RedisService;
import com.msm.admin.utils.ExceptionThrower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author quavario@gmail.com
 * @date 2021/3/25 16:11
 */
@Component
public class RetryUserBlocker {

    @Autowired
    HttpServletRequest request;

    @Autowired
    RedisService redisService;

    // 记录登录失败的ip
    public int loginFail(String username) {
        String key = "blockUser:" + IpUtils.getIpAddr(request) + ":" + username;
        int loginFailCount = redisService.hasKey(key) ? ((int) redisService.get(key)) + 1 : 1;
        redisService.set(key, loginFailCount, 60 * 60 * 24 * 30);
        return loginFailCount;
    }

    public int failIpCount() {
        String key = "blockUser:" + IpUtils.getIpAddr(request) + ":*";
        return redisService.hasKey(key) ? ((int) redisService.get(key)) : 0;
    }

    public boolean testIpAvailable() {
        int loginFailCount = failIpCount();
        return loginFailCount <= 10;
    }
}
