package com.msm.admin.framework.aop;

import com.msm.admin.framework.annotation.View;
import com.msm.admin.modules.analysis.entity.StatView;
import com.msm.admin.modules.analysis.service.StatViewService;
import com.msm.admin.utils.SubjectUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * web端访问记录
 * @author quavario@gmail.com
 * @date 2020/1/8 9:45
 */
@Aspect
@Component
@Slf4j
public class StatViewAspect {

    @Autowired
    private StatViewService monViewService;

    @Pointcut("@annotation(com.msm.admin.framework.annotation.View)")
    public void view() {}


    @Before("@annotation(com.msm.admin.framework.annotation.View)")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        //判断是否是后台管理
        Boolean admin = SubjectUtils.isAdminNull();
        if (!admin){
            return;
        }

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = signature.getParameterNames();

        if (parameterNames.length != 1 || !"id".equals(parameterNames[0])) {
            throw new RuntimeException("@View注解标注错误");
        }

        View view = signature.getMethod().getAnnotation(View.class);

        StatView statView = new StatView();


        if (view != null) {
            statView.setType(view.type());
        }

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        System.out.println(userAgent);

        statView.setRecordDate(new Date())
                .setItemId(args[0].toString())
                .setVisitorIp(request.getRemoteHost())
                .setBrowser(userAgent.getBrowser().getName())
                .setOs(userAgent.getOperatingSystem().getName())
                .setDevice(userAgent.getOperatingSystem().getDeviceType().getName())
                .setUserAgent(request.getHeader("User-Agent"));

        monViewService.save(statView);
    }
}
