package com.msm.admin.framework.exception;

/**
 * @author quavario@gmail.com
 * @date 2020/3/3 20:14
 */
//@AllArgsConstructor

public class IpLockedException extends BaseException {
    public IpLockedException() {
        super();
    }

    public IpLockedException(String message) {
        super(message);
    }

    public IpLockedException(String message, Throwable cause) {
        super(message, cause);
    }

    public IpLockedException(Throwable cause) {
        super(cause);
    }

    protected IpLockedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
