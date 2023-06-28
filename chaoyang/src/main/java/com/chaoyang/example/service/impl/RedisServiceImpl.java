package com.chaoyang.example.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.chaoyang.example.constant.RedisKeyConstant;
import com.chaoyang.example.entity.dto.LoginInfo;
import com.chaoyang.example.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Redis服务层服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/5/25
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public List<String> getTokens(Long userId) {
        String tokensJsonStr = this.redisTemplate.opsForValue().get(String.format(RedisKeyConstant.TOKENS, userId));

        if (Objects.isNull(tokensJsonStr)) {
            return null;
        }

        return new LinkedList<>(JSONArray.parseArray(tokensJsonStr, String.class));
    }

    @Override
    public Long getUserId(String token) {
        String userIdJsonStr = this.redisTemplate.opsForValue().get(String.format(RedisKeyConstant.USER_ID, token));

        if (Objects.isNull(userIdJsonStr)) {
            return null;
        }

        return Long.valueOf(userIdJsonStr);
    }

    @Override
    public String getCaptcha(String nonce) {
        return this.redisTemplate.opsForValue().get(String.format(RedisKeyConstant.CAPTCHA, nonce));
    }

    @Override
    public LoginInfo getLoginInfo(Long userId) {
        String loginInfoJsonStr = this.redisTemplate.opsForValue().get(String.format(RedisKeyConstant.LOGIN_INFO, userId));

        if (Objects.isNull(loginInfoJsonStr)) {
            return null;
        }

        return JSONObject.parseObject(loginInfoJsonStr, LoginInfo.class);
    }

    @Async("threadPoolTaskExecutor")
    @Override
    public void setTokens(Long userId, List<String> tokens) {
        this.redisTemplate.opsForValue().set(String.format(RedisKeyConstant.TOKENS, userId), JSONObject.toJSONString(tokens));
    }

    @Async("threadPoolTaskExecutor")
    @Override
    public void setTokens(Long userId, List<String> tokens, Duration expire) {
        this.redisTemplate.opsForValue().set(String.format(RedisKeyConstant.TOKENS, userId), JSONObject.toJSONString(tokens), expire);
    }

    @Async("threadPoolTaskExecutor")
    @Override
    public void setUserId(String token, Long userId, Duration expire) {
        this.redisTemplate.opsForValue().set(String.format(RedisKeyConstant.USER_ID, token), String.valueOf(userId), expire);
    }

    @Async("threadPoolTaskExecutor")
    @Override
    public void setCaptcha(String nonce, String captcha, Duration expire) {
        this.redisTemplate.opsForValue().set(String.format(RedisKeyConstant.CAPTCHA, nonce), captcha, expire);
    }

    @Async("threadPoolTaskExecutor")
    @Override
    public void setLoginInfo(Long userId, LoginInfo loginInfo, Duration expire) {
        this.redisTemplate.opsForValue().set(String.format(RedisKeyConstant.LOGIN_INFO, userId), JSONObject.toJSONString(loginInfo, JSONWriter.Feature.WriteNullListAsEmpty), expire);
    }

    @Override
    public boolean deleteTokens(Long userId) {
        return Objects.equals(this.redisTemplate.delete(String.format(RedisKeyConstant.TOKENS, userId)), Boolean.TRUE);
    }

    @Override
    public boolean deleteUserId(String token) {
        return Objects.equals(this.redisTemplate.delete(String.format(RedisKeyConstant.USER_ID, token)), Boolean.TRUE);
    }

    @Override
    public boolean deleteCaptcha(String nonce) {
        return Objects.equals(this.redisTemplate.delete(String.format(RedisKeyConstant.CAPTCHA, nonce)), Boolean.TRUE);
    }

    @Override
    public boolean deleteLoginInfo(Long userId) {
        return Objects.equals(this.redisTemplate.delete(String.format(RedisKeyConstant.LOGIN_INFO, userId)), Boolean.TRUE);
    }

}