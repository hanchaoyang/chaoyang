package com.chaoyang.example.controller;

import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.GetQrCodeRequest;
import com.chaoyang.example.entity.dto.request.LoginRequest;
import com.chaoyang.example.entity.dto.response.LoginInfoResponse;
import com.chaoyang.example.entity.dto.response.LoginResponse;
import com.chaoyang.example.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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

    @GetMapping("/qrcode")
    public Result<Void> getQrCode(GetQrCodeRequest getQrCodeRequest, HttpServletResponse httpServletResponse) {
        this.loginService.getQrCode(getQrCodeRequest, httpServletResponse);

        return Result.success();
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = this.loginService.login(loginRequest);

        return Result.success(loginResponse);
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        this.loginService.logout(token);

        return Result.success();
    }

    @GetMapping("/login-info")
    public Result<LoginInfoResponse> getLoginInfo(@RequestHeader("Authorization") String token) {
        LoginInfoResponse loginInfoResponse = this.loginService.getLoginInfo(token);

        return Result.success(loginInfoResponse);
    }

}