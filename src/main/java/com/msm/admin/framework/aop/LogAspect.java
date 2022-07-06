package com.msm.admin.framework.aop;

import com.msm.admin.framework.annotation.Log;
import com.msm.admin.modules.monitor.entity.SysLog;
import com.msm.admin.modules.monitor.service.SysLogService;
import com.msm.admin.modules.system.entity.SysUser;
import com.msm.admin.utils.JsonUtils;
import com.msm.admin.utils.SubjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author quavario@gmail.com
 * @date 2020/1/8 9:45
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private SysLogService logService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /*@Autowired
    RabbitSender rabbitSender;*/

//    @Pointcut("execution(public * com.msm.admin.modules.*.controller.*.*(..))")
//    public void controller() { }

    @Pointcut("@annotation(com.msm.admin.framework.annotation.Log)")
    public void log() {}


    @Around("log()&&@annotation(l)")
    public Object doAround(ProceedingJoinPoint joinPoint, Log l) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();




        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        Map map = new HashMap();
        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], args[i]);
        }



        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        SysLog sysLog = new SysLog();

        SysUser currentUser = SubjectUtils.getCurrentUser();
        if (l != null) {
            sysLog.setContent(l.value());
            sysLog.setType(l.type());
        }

        sysLog.setIp(getIpAddr(request)).setRequestParam(JsonUtils.objectToJson(map))
                .setMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName())
                .setUsername(SubjectUtils.getCurrentUser().getLoginName())
                .setUserId(SubjectUtils.getCurrentUser().getId())
                .setRequestType(request.getMethod())
                .setRequestUrl(request.getRequestURI())
                .setRequestTime(end - start)
                .setRecordDate(new Date());
//        logService.save(sysLog);
        //rabbitSender.routeLog().send(sysLog);
        return result;
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
