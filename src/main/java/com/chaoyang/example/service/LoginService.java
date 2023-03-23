package com.chaoyang.example.service;

import com.chaoyang.example.entity.dto.request.GetQrCodeRequest;
import com.chaoyang.example.entity.dto.request.LoginRequest;
import com.chaoyang.example.entity.dto.response.LoginInfoResponse;
import com.chaoyang.example.entity.dto.response.LoginResponse;

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
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 退出
     */
    void logout(String token);

    /**
     * 获取登录信息
     */
    LoginInfoResponse getLoginInfo(String token);

}