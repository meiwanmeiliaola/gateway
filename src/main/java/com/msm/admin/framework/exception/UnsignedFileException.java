package com.msm.admin.framework.exception;

/**
 * @author quavario@gmail.com
 * @date 2020/3/3 20:14
 */
public class UnsignedFileException extends BaseException {
    public UnsignedFileException() {
        super();
    }

    public UnsignedFileException(String message) {
        super(message);
    }

    public UnsignedFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsignedFileException(Throwable cause) {
        super(cause);
    }

    protected UnsignedFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
