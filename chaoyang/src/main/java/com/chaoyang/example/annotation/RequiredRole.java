package com.chaoyang.example.annotation;

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
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredRole {

    String[] value() default {};

    boolean and() default false;

}