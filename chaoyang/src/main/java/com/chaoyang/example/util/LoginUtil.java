package com.chaoyang.example.util;

import com.chaoyang.example.entity.dto.LoginInfo;
import com.chaoyang.example.exception.AuthenticationException;
import com.chaoyang.example.exception.ParameterException;
import com.chaoyang.example.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 登录工具类
 *
 * @author 韩朝阳
 * @since 2023/4/21
 */
@Component
@RequiredArgsConstructor
public class LoginUtil {

    private final RedisService redisService;

    /**
     * 获取Token
     */
    public String getToken() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (Objects.isNull(servletRequestAttributes)) {
            return null;
        }

        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();

        return httpServletRequest.getHeader("Authorization");
    }

    /**
     * 获取登录信息
     */
    public LoginInfo getLoginInfo() {
        String token = this.getToken();

        if (Objects.isNull(token)) {
            throw new ParameterException(ParameterException.Message.NO_TOKEN);
        }

        Long userId = this.redisService.getUserId(token);

        if (Objects.isNull(userId)) {
            throw new AuthenticationException(AuthenticationException.Message.NOT_LOGIN);
        }

        return this.redisService.getLoginInfo(userId);
    }

}