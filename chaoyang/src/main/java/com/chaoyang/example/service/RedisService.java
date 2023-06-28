package com.chaoyang.example.service;

import com.chaoyang.example.entity.dto.LoginInfo;

import java.time.Duration;
import java.util.List;

/**
 * Redis服务层接口
 *
 * @author 韩朝阳
 * @since 2023/5/25
 */
public interface RedisService {

    /**
     * 获取token集合
     */
    List<String> getTokens(Long userId);

    /**
     * 获取用户ID
     */
    Long getUserId(String token);

    /**
     * 获取验证码
     */
    String getCaptcha(String nonce);

    /**
     * 获取登录信息
     */
    LoginInfo getLoginInfo(Long userId);

    /**
     * 缓存Token集合（异步）
     */
    void setTokens(Long userId, List<String> tokens);

    /**
     * 缓存Token集合（异步）
     */
    void setTokens(Long userId, List<String> tokens, Duration expire);

    /**
     * 缓存用户ID（异步）
     */
    void setUserId(String token, Long userId, Duration expire);

    /**
     * 缓存验证码（异步）
     */
    void setCaptcha(String nonce, String captcha, Duration expire);

    /**
     * 缓存登录信息（异步）
     */
    void setLoginInfo(Long userId, LoginInfo loginInfo, Duration expire);

    /**
     * 删除Token集合
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean deleteTokens(Long userId);

    /**
     * 删除用户ID
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean deleteUserId(String token);

    /**
     * 删除验证码
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean deleteCaptcha(String nonce);

    /**
     * 删除登录信息
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean deleteLoginInfo(Long userId);

}