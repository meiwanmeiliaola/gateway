package com.msm.admin.framework.exception;

/**
 * @author quavario@gmail.com
 * @date 2020/12/31 16:14
 */
public class AccountLockedException extends BaseException {
    public AccountLockedException() {
        super();
    }

    public AccountLockedException(String message) {
        super(message);
    }

    public AccountLockedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountLockedException(Throwable cause) {
        super(cause);
    }

    protected AccountLockedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
