package com.msm.admin.framework.exception;

/**
 * @author quavario@gmail.com
 * @date 2020/3/3 20:14
 */
//@AllArgsConstructor

public class NameIsTakenException extends BaseException {
    public NameIsTakenException() {
        super();
    }

    public NameIsTakenException(String message) {
        super(message);
    }

    public NameIsTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public NameIsTakenException(Throwable cause) {
        super(cause);
    }

    protected NameIsTakenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
