package com.chaoyang.example.exception;

/**
 * 业务异常
 *
 * @author 韩朝阳
 * @since 2023/3/20
 */
public class BusinessException extends RuntimeException {

    public BusinessException() {

    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getCode() {
        return 500;
    }

}