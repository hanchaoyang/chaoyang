package com.chaoyang.example.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 通用返回包装类
 *
 * @author 韩朝阳
 * @since 2023/3/20
 */
@Getter
@Setter
@ToString
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 20230320142400L;

    private Integer code;

    private String message;

    private T data;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result<Void> success() {
        return new Result<>(200, "成功");
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "成功", data);
    }

    public static Result<Void> error() {
        return new Result<>(500, "服务器内部错误");
    }

    public static <T> Result<T> error(T data) {
        return new Result<>(500, "服务器内部错误", data);
    }

    public static Result<Void> of(Integer code, String message) {
        return new Result<>(code, message);
    }

    public static <T> Result<T> of(Integer code, String message, T data) {
        return new Result<>(code, message, data);
    }

}