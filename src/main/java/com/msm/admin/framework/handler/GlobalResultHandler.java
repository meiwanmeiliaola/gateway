package com.msm.admin.framework.handler;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msm.admin.modules.common.entity.PageResultBean;
import com.msm.admin.modules.common.entity.ResultBean;
import com.msm.admin.modules.common.service.impl.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author quavario@gmail.com
 * @date 2019/12/16 18:12
 */
@EnableWebMvc
@Configuration
public class GlobalResultHandler {

    @Autowired
    private ResultService resultService;

    @RestControllerAdvice
    static class ResultResponseAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

            if (o instanceof IPage) {
                PageResultBean resultBean = PageResultBean.build((IPage) o);
                return resultBean;
            } else if (o instanceof ResultBean) {
                return o;
            }
            return ResultBean.build(o);
        }
    }
}
