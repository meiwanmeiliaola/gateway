package com.msm.admin.framework.exception;

import com.msm.admin.common.enums.ErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author quavario@gmail.com
 * @date 2020/3/3 20:09
 */
@AllArgsConstructor
public class UserNameIsTakenException extends BaseException {
    public UserNameIsTakenException(String message) {
        super(message);
    }

    public UserNameIsTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameIsTakenException(Throwable cause) {
        super(cause);
    }

    protected UserNameIsTakenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
