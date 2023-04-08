package com.chaoyang.example.aop;

import com.alibaba.fastjson2.JSONObject;
import com.chaoyang.example.annotation.RequiredPermission;
import com.chaoyang.example.annotation.RequiredRole;
import com.chaoyang.example.entity.dto.LoginInfo;
import com.chaoyang.example.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 认证切面
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthAspect {

    private final RedisTemplate<String, String> redisTemplate;

    // TODO 方法参数直接获取注解
    @Around("@within(com.chaoyang.example.annotation.RequiredRole) || @within(com.chaoyang.example.annotation.RequiredPermission) || @annotation(com.chaoyang.example.annotation.RequiredRole) || @annotation(com.chaoyang.example.annotation.RequiredPermission)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
        /*
         * 获取登录信息
         */
        LoginInfo loginInfo = this.getLoginInfo(this.getToken());

        /*
         * 认证角色和权限
         */
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        this.auth(methodSignature.getMethod(), loginInfo);

        /*
         * 执行方法
         */
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private String getToken() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (Objects.isNull(servletRequestAttributes)) {
            throw new RuntimeException("服务器内部错误");
        }

        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();

        String token = httpServletRequest.getHeader("Authorization");

        if (Objects.isNull(token)) {
            throw new AuthException("无权限");
        }

        return token;
    }

    private LoginInfo getLoginInfo(String token) {
        String loginInfoStr = this.redisTemplate.opsForValue().get(String.format("chaoyang:login-info:%s", token));

        if (Objects.isNull(loginInfoStr)) {
            throw new AuthException("无权限");
        }

        return JSONObject.parseObject(loginInfoStr, LoginInfo.class);
    }

    private void auth(Method method, LoginInfo loginInfo) {
        /*
         * 认证角色
         */
        RequiredRole methodRequiredRole = method.getAnnotation(RequiredRole.class);

        if (Objects.isNull(methodRequiredRole)) {
            Class<?> clazz = method.getDeclaringClass();

            RequiredRole classRequiredRole = clazz.getAnnotation(RequiredRole.class);

            if (Objects.nonNull(classRequiredRole)) {
                this.authRole(classRequiredRole, loginInfo.getUserRoles());
            }
        } else {
            this.authRole(methodRequiredRole, loginInfo.getUserRoles());
        }

        /*
         * 认证权限
         */
        RequiredPermission methodRequiredPermission = method.getAnnotation(RequiredPermission.class);

        if (Objects.isNull(methodRequiredPermission)) {
            Class<?> clazz = method.getDeclaringClass();

            RequiredPermission classRequiredPermission = clazz.getAnnotation(RequiredPermission.class);

            if (Objects.nonNull(classRequiredPermission)) {
                this.authPermission(classRequiredPermission, loginInfo.getUserRoles());
            }
        } else {
            this.authPermission(methodRequiredPermission, loginInfo.getUserRoles());
        }
    }

    private void authRole(RequiredRole requiredRole, Collection<String> userRoles) {
        Set<String> requiredRoles = Arrays.stream(requiredRole.roles()).collect(Collectors.toSet());

        if (requiredRole.and()) {
            if (!userRoles.containsAll(requiredRoles)) {
                throw new AuthException("无权限");
            }
        } else {
            if (requiredRoles.stream().noneMatch(userRoles::contains)) {
                throw new AuthException("无权限");
            }
        }
    }

    private void authPermission(RequiredPermission requiredPermission, Collection<String> userPermissions) {
        Set<String> requiredPermissions = Arrays.stream(requiredPermission.permissions()).collect(Collectors.toSet());

        if (requiredPermission.and()) {
            if (!userPermissions.containsAll(requiredPermissions)) {
                throw new AuthException("无权限");
            }
        } else {
            if (requiredPermissions.stream().noneMatch(userPermissions::contains)) {
                throw new AuthException("无权限");
            }
        }
    }

}