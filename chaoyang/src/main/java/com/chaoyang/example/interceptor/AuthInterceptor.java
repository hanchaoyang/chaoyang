package com.chaoyang.example.interceptor;

import com.alibaba.fastjson2.JSON;
import com.chaoyang.example.annotation.RequiredLogin;
import com.chaoyang.example.annotation.RequiredPermission;
import com.chaoyang.example.annotation.RequiredRole;
import com.chaoyang.example.constant.RedisKeyConstant;
import com.chaoyang.example.entity.dto.LoginInfo;
import com.chaoyang.example.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限认证拦截器
 *
 * @author 韩朝阳
 * @since 2023/4/24
 */
@Configuration
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (!handlerMethod.hasMethodAnnotation(RequiredLogin.class) && !handlerMethod.hasMethodAnnotation(RequiredRole.class) && !handlerMethod.hasMethodAnnotation(RequiredPermission.class)) {
            return true;
        }

        String token = request.getHeader("Authorization");

        if (Objects.isNull(token)) {
            throw new AuthException("非法请求");
        }

        String loginInfoJsonStr = this.redisTemplate.opsForValue().get(String.format(RedisKeyConstant.LOGIN_INFO, token));

        if (Objects.isNull(loginInfoJsonStr)) {
            throw new AuthException("未登录");
        }

        RequiredRole requiredRole = handlerMethod.getMethodAnnotation(RequiredRole.class);
        RequiredPermission requiredPermission = handlerMethod.getMethodAnnotation(RequiredPermission.class);

        if (Objects.nonNull(requiredRole) && Objects.nonNull(requiredPermission)) {
            return true;
        }

        LoginInfo loginInfo = JSON.parseObject(loginInfoJsonStr, LoginInfo.class);

        if (Objects.nonNull(requiredRole)) {
            Set<String> requireRoleCodes = Arrays.stream(requiredRole.value()).collect(Collectors.toSet());

            if (!requireRoleCodes.isEmpty()) {
                Set<String> roleCodes = loginInfo.getUserRoles();

                if (requiredRole.and()) {
                    if (!roleCodes.containsAll(requireRoleCodes)) {
                        throw new AuthException("缺少必要的角色");
                    }
                } else {
                    if (roleCodes.stream().noneMatch(requireRoleCodes::contains)) {
                        throw new AuthException("缺少必要的角色");
                    }
                }
            }
        }

        if (Objects.nonNull(requiredPermission)) {
            Set<String> requirePermissionCodes = Arrays.stream(requiredPermission.value()).collect(Collectors.toSet());

            if (!requirePermissionCodes.isEmpty()) {
                Set<String> permissionCodes = loginInfo.getUserPermissions();

                if (requiredPermission.and()) {
                    if (!permissionCodes.containsAll(requirePermissionCodes)) {
                        throw new AuthException("缺少必要的权限");
                    }
                } else {
                    if (permissionCodes.stream().noneMatch(requirePermissionCodes::contains)) {
                        throw new AuthException("缺少必要的权限");
                    }
                }
            }
        }

        return true;
    }

}