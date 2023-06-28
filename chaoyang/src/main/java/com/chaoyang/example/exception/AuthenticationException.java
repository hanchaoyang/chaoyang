package com.chaoyang.example.exception;

/**
 * 认证异常
 *
 * @author 韩朝阳
 * @since 2023/6/1
 */
public class AuthenticationException extends RuntimeException {

    /**
     * 响应码，400~419，前端需退出登录
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static final int code = 400;

    public AuthenticationException() {

    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    public AuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static int getCode() {
        return AuthenticationException.code;
    }

    public static class Message {

        public static final String CAPTCHA_ERROR = "验证码错误";

        public static final String USER_NOT_EXISTS_OR_PASSWORD_ERROR = "用户不存在或密码错误";

        public static final String USER_DISABLED = "用户已禁用";

        public static final String NOT_LOGIN = "未登录";

    }

}