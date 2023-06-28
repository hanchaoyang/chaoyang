package com.chaoyang.example.exception;

/**
 * 服务器内部异常
 *
 * @author 韩朝阳
 * @since 2023/6/1
 */
public class ServerException extends RuntimeException {

    /**
     * 响应码，9XX，不向用户输出详细提示
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static final int code = 900;

    public ServerException() {

    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static int getCode() {
        return ServerException.code;
    }

}