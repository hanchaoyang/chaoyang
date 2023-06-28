package com.chaoyang.example.exception;

/**
 * 授权异常
 *
 * @author 韩朝阳
 * @since 2023/6/1
 */
public class AuthorizationException extends RuntimeException {

    /**
     * 响应码，420~439，前端无需退出登录
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static final int code = 420;

    public AuthorizationException() {

    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException(Throwable cause) {
        super(cause);
    }

    public AuthorizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static int getCode() {
        return AuthorizationException.code;
    }

}