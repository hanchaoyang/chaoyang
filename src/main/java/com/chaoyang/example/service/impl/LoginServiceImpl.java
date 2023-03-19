package com.chaoyang.example.service.impl;

import com.chaoyang.example.entity.dto.request.GetQrCodeRequest;
import com.chaoyang.example.entity.dto.request.LoginRequest;
import com.chaoyang.example.service.LoginService;
import com.chaoyang.example.service.UserService;
import com.hanchaoyang.status.BusinessException;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

/**
 * 登录服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final RedisTemplate<String, String> redisTemplate;

    private final UserService userService;

    @Override
    public void getQrCode(GetQrCodeRequest getQrCodeRequest, HttpServletResponse httpServletResponse) {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(120, 40, 2);

        String qrcode = captcha.text();

        this.redisTemplate.opsForValue().set(String.format("chaoyang:qrcode:%s", getQrCodeRequest.getNonce()), qrcode, Duration.ofMinutes(1L));

        try {
            captcha.out(httpServletResponse.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String login(LoginRequest loginRequest) {
        String qrcode = this.redisTemplate.opsForValue().get(String.format("chaoyang:qrcode:%s", loginRequest.getNonce()));

        if (Objects.equals(loginRequest.getQrcode(), qrcode)) {
            this.redisTemplate.delete(String.format("chaoyang:qrcode:%s", loginRequest.getNonce()));
        } else {
            throw new BusinessException("验证码错误");
        }

        return UUID.randomUUID().toString();
    }

}