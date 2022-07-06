package com.msm.admin.framework.exception;

/**
 * @author quavario@gmail.com
 * @date 2020/3/3 20:14
 */
public class UnsupportedFileTypeException extends BaseException {
    public UnsupportedFileTypeException() {
        super();
    }

    public UnsupportedFileTypeException(String message) {
        super(message);
    }

    public UnsupportedFileTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedFileTypeException(Throwable cause) {
        super(cause);
    }

    protected UnsupportedFileTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
