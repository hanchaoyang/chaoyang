package com.chaoyang.example.service;

import com.chaoyang.example.entity.dto.request.GetQrCodeRequest;
import com.chaoyang.example.entity.dto.request.LoginRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录服务层接口
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
public interface LoginService {

    /**
     * 获取验证码
     */
    void getQrCode(GetQrCodeRequest getQrCodeRequest, HttpServletResponse httpServletResponse);

    /**
     * 登录
     */
    String login(LoginRequest loginRequest);

    /**
     * 退出
     */
    void logout(String token);

}