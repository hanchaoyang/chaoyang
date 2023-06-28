package com.chaoyang.example.service;

import com.chaoyang.example.entity.dto.request.GetCaptchaRequest;
import com.chaoyang.example.entity.dto.request.LoginRequest;
import com.chaoyang.example.entity.dto.response.LoginInfoResponse;
import com.chaoyang.example.entity.dto.response.LoginResponse;
import com.chaoyang.example.entity.po.User;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

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
    void getCaptcha(GetCaptchaRequest request, HttpServletResponse httpResponse);

    /**
     * 登录
     */
    LoginResponse login(LoginRequest request);

    /**
     * 退出
     */
    void logout();

    /**
     * 获取登录信息
     */
    LoginInfoResponse getLoginInfo();

}