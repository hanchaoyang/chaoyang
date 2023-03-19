package com.chaoyang.example.controller;

import com.chaoyang.example.entity.dto.request.GetQrCodeRequest;
import com.chaoyang.example.entity.dto.request.LoginRequest;
import com.chaoyang.example.service.LoginService;
import com.hanchaoyang.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<String> login(@RequestBody LoginRequest loginRequest) {
        String token = this.loginService.login(loginRequest);

        return Result.success(token);
    }

}