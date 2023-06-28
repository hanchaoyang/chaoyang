package com.chaoyang.example.exception;

/**
 * 参数异常
 *
 * @author 韩朝阳
 * @since 2023/3/20
 */
//@SuppressWarnings("unused")
public class ParameterException extends RuntimeException {

    /**
     * 响应码，3XX，用户可读的错误提示
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static final int code = 300;

    public ParameterException() {

    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterException(Throwable cause) {
        super(cause);
    }

    public ParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static int getCode() {
        return ParameterException.code;
    }

    public static class Message {

        public static final String NO_TOKEN = "无Token";

        public static final String USER_STATUS_ERROR = "用户状态错误";

    }

}