package com.msm.admin.framework.handler;

import com.msm.admin.common.constant.RabbitRoutingQueue;
import com.msm.admin.framework.exception.*;
import com.msm.admin.modules.common.entity.IpUtils;
import com.msm.admin.modules.common.entity.ResultBean;
import com.msm.admin.modules.common.service.impl.RedisService;
import com.msm.admin.modules.monitor.entity.SysException;
import com.msm.admin.security.RetryUserBlocker;
import com.msm.admin.utils.SubjectUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author quavario@gmail.com
 * @date 2020/1/13 16:30
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //@Autowired
    //RabbitSender rabbitSender;

    @Autowired
    RedisService redisService;

    @Autowired
    HttpServletRequest request;



    @ExceptionHandler({UnauthorizedException.class})
    public ResultBean handleUnauthorizedException(UnauthorizedException e, HttpServletResponse response) {
        exceptionCollectionProducer(e);
        e.printStackTrace();
        ResultBean resultBean = new ResultBean<>();
        resultBean.setMsg("权限不足");
        response.setStatus(403);
        return resultBean;
    }

    @ExceptionHandler({IncorrectCredentialsException.class})
    public ResultBean handleIncorrectCredentialsException(IncorrectCredentialsException e, HttpServletResponse response) {
        exceptionCollectionProducer(e);
        e.printStackTrace();
        ResultBean resultBean = new ResultBean<>();
        resultBean.setMsg("用户名和密码错误");
        response.setStatus(401);
        return resultBean;
    }

//    @ExceptionHandler({IpLockedException.class})
//    public ResultBean ipLockedExceptionException(IpLockedException e, HttpServletResponse response) {
//        exceptionCollectionProducer(e);
//        e.printStackTrace();
//        ResultBean resultBean = new ResultBean<>();
//        resultBean.setMsg("IP被锁定");
//        response.setStatus(401);
//        return resultBean;
//    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResultBean handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletResponse response) {
        exceptionCollectionProducer(e);

        BindingResult result = e.getBindingResult();
        List<ObjectError> errors = result.getAllErrors();
        String message = errors.get(0).getDefaultMessage();

        e.printStackTrace();
        ResultBean<Object> resultBean = new ResultBean<>();
        response.setStatus(422);
        resultBean.setMsg(message);
        return resultBean;
    }

    @ExceptionHandler({NullPointerException.class})
    public ResultBean handleNullPointerException(NullPointerException e, HttpServletResponse response) {
        String ipAddr = IpUtils.getIpAddr(request);

        exceptionCollectionProducer(e);
        e.printStackTrace();
        ResultBean<Object> resultBean = new ResultBean<>();
        resultBean.setMsg("资源不存在");
        response.setStatus(404);
        return resultBean;
    }

//    @ExceptionHandler({NameIsTakenException.class})
//    public ResultBean handleNameIsTakenException(NameIsTakenException e, HttpServletResponse response) {
//        exceptionCollectionProducer(e);
//
//        e.printStackTrace();
//        response.setStatus(422);
//        return ResultBean.error(e.getErrorInfo());
//    }
//
//    @ExceptionHandler({UserNameIsTakenException.class})
//    public ResultBean handleUserNameIsTakenException(UserNameIsTakenException e, HttpServletResponse response) {
//        return exceptionWrapper(e, response);
//    }
//
//    @ExceptionHandler({UnsupportedFileTypeException.class})
//    public ResultBean handleUnsupportedFileTypeException(UnsupportedFileTypeException e, HttpServletResponse response) {
//        return exceptionWrapper(e, response);
//    }
//
//    @ExceptionHandler({UnsignedFileException.class})
//    public ResultBean handleUnsignedFileException(UnsignedFileException e, HttpServletResponse response) {
//        return exceptionWrapper(e, response);
//    }
//
//
//    @ExceptionHandler({UnformatArchiveException.class})
//    public ResultBean handleUnformatArchiveException(UnformatArchiveException e, HttpServletResponse response) {
//        return exceptionWrapper(e, response);
//    }
//
//    @ExceptionHandler({UnKnownUserException.class})
//    public ResultBean handleUnKnownUserException(UnKnownUserException e, HttpServletResponse response) {
//        return exceptionWrapper(e, response);
//    }

    @ExceptionHandler({BaseException.class})
    public ResultBean<BaseException> baseExceptionHandler(BaseException e, HttpServletResponse response) {
        return exceptionWrapper(e, response);
    }

    @ExceptionHandler({Exception.class})
    public ResultBean exceptionHandler(Exception e, HttpServletResponse response) {
        exceptionCollectionProducer(e);

        e.printStackTrace();
        ResultBean<Object> resultBean = new ResultBean<>();
        resultBean.setMsg("unknown error");
        response.setStatus(500);
        return resultBean;
    }

    private ResultBean<BaseException> exceptionWrapper(BaseException e, HttpServletResponse response) {
        exceptionCollectionProducer(e);
        e.printStackTrace();
        ResultBean<BaseException> resultBean = new ResultBean<>();
        resultBean.setMsg(e.getErrorInfo().getMessage());
        resultBean.setCode(e.getErrorInfo().getCode());
        response.setStatus(e.getErrorInfo().getCode());
        return resultBean;
    }

    private void exceptionCollectionProducer(Throwable e) {

        // 获取异常堆栈信息
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);

        // 获取请求参数
        String requestParam = null;
        try {
            ServletInputStream inputStream = request.getInputStream();
            requestParam = IOUtils.toString(inputStream, "utf-8").trim();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        SysException sysException = new SysException();
        sysException.setIp(getIpAddr(request))
                .setMessage(stringWriter.toString())
                .setRecordDate(new Date())
                .setRequestType(request.getMethod())
                .setRequestUrl(request.getRequestURI())
                .setRequestParam(requestParam)
                .setUserId(Optional.ofNullable(SubjectUtils.getCurrentUser().getId()).orElse(null))
                .setUsername(Optional.ofNullable(SubjectUtils.getCurrentUser().getLoginName()).orElse(null));

//        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitRoutingQueue.EXCEPTION_ROUTING, sysException);

        // 停用rabbitmq
        // TODO
//        rabbitSender.routeException().send(sysException);

    }
    private String getIpAddr(HttpServletRequest request) {
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
