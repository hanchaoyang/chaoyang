package com.chaoyang.example.exception;

import com.hanchaoyang.result.Result;
import com.hanchaoyang.status.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        return Result.of(e);
    }

    /**
     * 认证异常
     */
    @ExceptionHandler(AuthException.class)
    public Result<Void> handleAuthException(AuthException e) {
        return Result.of(e.getCode(), e.getMessage());
    }

    /**
     * 参数错误
     */

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> handleRuntimeException(RuntimeException e) {
        log.error("服务器内部错误", e);

        return Result.of(500, "服务器内部错误");
    }

}