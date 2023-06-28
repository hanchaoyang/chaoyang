package com.chaoyang.example.interceptor;

import com.chaoyang.example.annotation.RequiredLogin;
import com.chaoyang.example.annotation.RequiredPermission;
import com.chaoyang.example.annotation.RequiredRole;
import com.chaoyang.example.entity.dto.LoginInfo;
import com.chaoyang.example.exception.AuthorizationException;
import com.chaoyang.example.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
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

    private final LoginUtil loginUtil;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (!handlerMethod.hasMethodAnnotation(RequiredLogin.class) && !handlerMethod.hasMethodAnnotation(RequiredRole.class) && !handlerMethod.hasMethodAnnotation(RequiredPermission.class)) {
            return true;
        }

        LoginInfo loginInfo = this.loginUtil.getLoginInfo();

        RequiredRole requiredRole = handlerMethod.getMethodAnnotation(RequiredRole.class);
        RequiredPermission requiredPermission = handlerMethod.getMethodAnnotation(RequiredPermission.class);

        if (Objects.isNull(requiredRole) && Objects.isNull(requiredPermission)) {
            return true;
        }

        if (Objects.nonNull(requiredRole)) {
            Set<String> requireRoleCodes = Arrays.stream(requiredRole.value()).collect(Collectors.toSet());

            if (!requireRoleCodes.isEmpty()) {
                Set<String> roleCodes = loginInfo.getRoles();

                if (requiredRole.and()) {
                    if (!roleCodes.containsAll(requireRoleCodes)) {
                        throw new AuthorizationException("缺少必要的角色");
                    }
                } else {
                    if (roleCodes.stream().noneMatch(requireRoleCodes::contains)) {
                        throw new AuthorizationException("缺少必要的角色");
                    }
                }
            }
        }

        if (Objects.nonNull(requiredPermission)) {
            Set<String> requirePermissionCodes = Arrays.stream(requiredPermission.value()).collect(Collectors.toSet());

            if (!requirePermissionCodes.isEmpty()) {
                Set<String> permissionCodes = loginInfo.getPermissions();

                if (requiredPermission.and()) {
                    if (!permissionCodes.containsAll(requirePermissionCodes)) {
                        throw new AuthorizationException("缺少必要的权限");
                    }
                } else {
                    if (permissionCodes.stream().noneMatch(requirePermissionCodes::contains)) {
                        throw new AuthorizationException("缺少必要的权限");
                    }
                }
            }
        }

        return true;
    }

}