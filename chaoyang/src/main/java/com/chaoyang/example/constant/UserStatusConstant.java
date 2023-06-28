package com.chaoyang.example.constant;

import java.util.Objects;

/**
 * 用户状态常量类
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
public class UserStatusConstant {

    /**
     * 禁用
     */
    public static final int DISABLE = 0;

    /**
     * 启用
     */
    public static final int ENABLE = 1;

    /**
     * 校验状态值是否合法
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isValid(int status) {
        return Objects.equals(status, UserStatusConstant.DISABLE) || Objects.equals(status, UserStatusConstant.ENABLE);
    }

}