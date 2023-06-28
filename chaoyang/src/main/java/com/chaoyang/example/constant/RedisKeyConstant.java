package com.chaoyang.example.constant;

/**
 * Redis键常量
 *
 * @author 韩朝阳
 * @since 2023/4/23
 */
public class RedisKeyConstant {

    /**
     * Tokens（变量为用户ID）
     */
    public static final String TOKENS = "tokens:%d";

    /**
     * 用户ID（变量为Token）
     */
    public static final String USER_ID = "user-id:%s";

    /**
     * 验证码（变量为获取验证码时的临时字符串）
     */
    public static final String CAPTCHA = "captcha:%s";

    /**
     * 登录信息（变量为用户ID）
     */
    public static final String LOGIN_INFO = "login-info:%s";

}