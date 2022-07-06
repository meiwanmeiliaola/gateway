package com.msm.admin.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author quavario@gmail.com
 * @date 2020/1/8 11:08
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String value() default "";
    // 类型 1 登录日志 2 操作日志
    int type() default 2;
}
