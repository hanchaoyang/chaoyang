package com.chaoyang.example.exception;

import cn.hutool.core.collection.CollectionUtil;
import com.chaoyang.example.entity.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        return Result.of(e.getCode(), e.getMessage());
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
    @ExceptionHandler(ParameterException.class)
    public Result<Void> handleParameterException(ParameterException e) {
        return Result.of(e.getCode(), e.getMessage());
    }

    /**
     * 参数错误
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Result.of(400, "参数错误");
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        List<ObjectError> errors = e.getAllErrors();

        if (CollectionUtil.isNotEmpty(errors)) {
            ObjectError error = errors.get(0);

            return Result.of(400, error.getDefaultMessage());
        }

        return Result.of(400, "参数错误");
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> handleRuntimeException(RuntimeException e) {
        log.error("服务器内部错误", e);

        return Result.of(500, "服务器内部错误");
    }

}