package com.chaoyang.example.annotation;

import com.chaoyang.example.constant.LogicEnum;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 角色注解
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Target({ElementType.TYPE, ElementType.METHOD})  // TODO 类上要不要
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredRole {

    @AliasFor("roles")
    String[] value() default {};

    @AliasFor("value")
    String[] roles() default {};

    LogicEnum logic() default LogicEnum.AND;

}