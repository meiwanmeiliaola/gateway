package com.msm.admin.framework.exception;

/**
 * @author quavario@gmail.com
 * @date 2020/12/31 16:14
 */
public class UnKnownUserException extends BaseException {
    public UnKnownUserException() {
        super();
    }

    public UnKnownUserException(String message) {
        super(message);
    }

    public UnKnownUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnKnownUserException(Throwable cause) {
        super(cause);
    }

    protected UnKnownUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
