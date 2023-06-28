package com.chaoyang.example.controller;

import com.chaoyang.example.annotation.RequiredLogin;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.GetCaptchaRequest;
import com.chaoyang.example.entity.dto.request.LoginRequest;
import com.chaoyang.example.entity.dto.response.LoginInfoResponse;
import com.chaoyang.example.entity.dto.response.LoginResponse;
import com.chaoyang.example.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 登录控制层
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/captcha")
    public void getCaptcha(@Valid GetCaptchaRequest request, HttpServletResponse httpResponse) {
        this.loginService.getCaptcha(request, httpResponse);
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = this.loginService.login(request);

        return Result.success(response);
    }

    @PostMapping("/logout")
    @RequiredLogin
    public Result<Void> logout() {
        this.loginService.logout();

        return Result.success();
    }

    @GetMapping("/login-info")
    @RequiredLogin
    public Result<LoginInfoResponse> getLoginInfo() {
        LoginInfoResponse response = this.loginService.getLoginInfo();

        return Result.success(response);
    }

}