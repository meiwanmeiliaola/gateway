package com.msm.admin.utils;

import com.msm.admin.common.enums.ErrorInfo;
import com.msm.admin.framework.exception.BaseException;
import com.msm.admin.framework.exception.NameIsTakenException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author quavario@gmail.com
 * @date 2020/3/4 22:21
 */
public class ExceptionThrower {
    public static void of(ErrorInfo errorInfo) {
        try {
            Constructor<? extends BaseException> constructor = errorInfo.getException().getConstructor(String.class);
            BaseException baseException = constructor.newInstance(errorInfo.getMessage()).setErrorInfo(errorInfo);
            throw baseException;



//            throw errorInfo.getException().newInstance().setErrorInfo(errorInfo);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
