package com.chaoyang.example.exception;

import com.chaoyang.example.entity.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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
     * 参数异常
     */
    @ExceptionHandler(ParameterException.class)
    public Result<Void> handleParameterException(ParameterException e) {
        return Result.of(ParameterException.getCode(), e.getMessage());
    }

    /**
     * 参数异常
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        List<ObjectError> errors = e.getAllErrors();

        if (!errors.isEmpty()) {
            ObjectError error = errors.get(0);

            return Result.of(ParameterException.getCode(), error.getDefaultMessage());
        }

        return Result.of(ParameterException.getCode(), "参数错误");
    }

    /**
     * 认证异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result<Void> handleAuthenticationException(AuthenticationException e) {
        return Result.of(AuthenticationException.getCode(), e.getMessage());
    }

    /**
     * 授权异常
     */
    @ExceptionHandler(AuthorizationException.class)
    public Result<Void> handleAuthorizationException(AuthorizationException e) {
        return Result.of(AuthorizationException.getCode(), e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        return Result.of(BusinessException.getCode(), e.getMessage());
    }

    /**
     * 服务器内部异常
     */
    @ExceptionHandler(ServerException.class)
    public Result<Void> handleServerException(ServerException e) {
        return Result.of(ServerException.getCode(), e.getMessage());
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> handleRuntimeException(RuntimeException e) {
        log.error("服务器内部异常", e);

        return Result.of(ServerException.getCode(), "服务器内部异常");
    }

}