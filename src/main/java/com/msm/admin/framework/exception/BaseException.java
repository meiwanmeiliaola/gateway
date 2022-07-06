package com.msm.admin.framework.exception;

import com.msm.admin.common.enums.ErrorInfo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author quavario@gmail.com
 * @date 2020/3/4 22:44
 */
@Data
@Accessors(chain = true)
public class BaseException extends RuntimeException {
    private ErrorInfo errorInfo;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
