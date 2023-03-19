package com.chaoyang.example.exception;

/**
 * 认证异常
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
public class AuthException extends RuntimeException {

    public AuthException() {

    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}