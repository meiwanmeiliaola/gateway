package com.msm.admin.framework.exception;

/**
 * @author quavario@gmail.com
 * @date 2020/3/3 20:14
 */
public class UnformatArchiveException extends BaseException {
    public UnformatArchiveException() {
        super();
    }

    public UnformatArchiveException(String message) {
        super(message);
    }

    public UnformatArchiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnformatArchiveException(Throwable cause) {
        super(cause);
    }

    protected UnformatArchiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
