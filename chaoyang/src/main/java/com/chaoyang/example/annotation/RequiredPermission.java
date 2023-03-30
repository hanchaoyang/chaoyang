package com.chaoyang.example.annotation;

import com.chaoyang.example.constant.LogicEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredPermission {

    String[] permissions() default {};

    LogicEnum logic() default LogicEnum.AND;

}