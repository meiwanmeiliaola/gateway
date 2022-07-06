//package com.msm.admin.config;
//
//import com.msm.admin.framework.filter.ExceptionFilter;
//import com.msm.admin.framework.filter.XssFilter;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.DispatcherType;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Filter配置
// *
// */
//@Configuration
//public class FilterConfig
//{
//    @Value("${xss.enabled}")
//    private String enabled;
//
//    @Value("${xss.excludes}")
//    private String excludes;
//
//    @Value("${xss.urlPatterns}")
//    private String urlPatterns;
//
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    @Bean
//    public FilterRegistrationBean xssFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new ExceptionFilter());
//        registration.addUrlPatterns("/*");
//        registration.setName("exceptionFilter");
//        registration.setOrder(1);
//        return registration;
//    }
//}
