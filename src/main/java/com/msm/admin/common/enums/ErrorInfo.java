package com.msm.admin.common.enums;

import com.msm.admin.framework.exception.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常信息，用于抛出异常，并返回前端
 * 使用方法 com.msm.admin.utils.ExceptionThrow.of(ErrorInfo errorInfo)
 * 注意：使用之前必须在此处添加ErrorInfo实例
 * @author quavario@gmail.com
 * @date 2020/3/4 22:07
 */

@AllArgsConstructor
@Getter
public enum ErrorInfo {

//    UNAUTHORIZED("权限不足", 403);
//    INCORRECT_CREDENTIALS("认证错误", 401),
//    ARGUMENT_NOT_VALID("校验失败", 422),
    USER_NAME_IS_TAKEN("登录名已经存在", 422, UserNameIsTakenException.class),
    NAME_IS_TAKEN("用户名已经存在", 422, NameIsTakenException.class),
    UNSUPPORTED_FILE_TYPE("不支持的文件类型", 415, UnsupportedFileTypeException.class),
    UNSIGNED_FILE("签名校验未通过，请签名后上传", 419, UnsignedFileException .class),
    UNFORMAT_ARCHIVE("压缩包格式错误", 420, UnformatArchiveException.class),
    UNKNOWN_USER("用户名或密码错误", 401, UnKnownUserException.class),
    ACCOUNT_LOCKED("账号被冻结", 401, AccountLockedException.class),
    TITLE_EXIST("该名称已经存在，请检查名称", 422, AccountLockedException.class),
    IP_LOCKED("登录错误次数过多，IP已被锁定", 401, IpLockedException.class);
    /**
     * 消息
     */
    private final String message;

    // 错误代码
    private final int code;

    // 抛出得异常类型
    private final Class<? extends BaseException> exception;
//    private RuntimeException exception;
}

