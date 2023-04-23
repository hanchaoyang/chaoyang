package com.chaoyang.example.interceptor;

import com.alibaba.fastjson2.JSON;
import com.chaoyang.example.annotation.RequiredLogin;
import com.chaoyang.example.constant.RedisKeyConstant;
import com.chaoyang.example.entity.dto.LoginInfo;
import com.chaoyang.example.exception.AuthException;
import com.chaoyang.example.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 登录拦截器
 *
 * @author 韩朝阳
 * @since 2023/4/21
 */
@Configuration
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, String> redisTemplate;

    // TODO 测试Bean能否注入和异常抛出是否能被全局异常处理器捕获
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            // 抛异常
            return false;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        RequiredLogin requiredLogin = handlerMethod.getMethodAnnotation(RequiredLogin.class);

        if (Objects.isNull(requiredLogin)) {
            return true;
        }

        String token = request.getHeader("Authorization");

        if (Objects.isNull(token)) {
            throw new AuthException("非法请求");
        }

        String loginInfoStr = this.redisTemplate.opsForValue().get(String.format(RedisKeyConstant.LOGIN_INFO, token));

        if (Objects.isNull(loginInfoStr)) {
            throw new AuthException("非法请求");
        }

        LoginInfo loginInfo = JSON.parseObject(loginInfoStr, LoginInfo.class);

        return true;
    }

}