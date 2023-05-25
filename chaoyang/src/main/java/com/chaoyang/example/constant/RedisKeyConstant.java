package com.chaoyang.example.constant;

/**
 * Redis键常量
 *
 * @author 韩朝阳
 * @since 2023/4/23
 */
public class RedisKeyConstant {

    /**
     * 登录信息
     */
    @Deprecated
    public static final String LOGIN_INFO = "login-info:%s";

    /**
     * Tokens（变量为用户ID）
     */
    public static final String TOKENS = "tokens:%d";

    /**
     * 用户ID（变量为token）
     */
    public static final String USER_ID = "user-id:%s";

    /**
     * 验证码（变量为获取验证码时的临时字符串）
     */
    public static final String CAPTCHA = "captcha:%s";

    /**
     * 用户（变量为用户ID）
     */
    public static final String USER = "user:%d";

    /**
     * 角色集合（变量为用户ID）
     */
    public static final String ROLES = "roles:%d";

    /**
     * 权限集合（变量为用户ID）
     */
    public static final String PERMISSIONS = "permissions:%d";

}