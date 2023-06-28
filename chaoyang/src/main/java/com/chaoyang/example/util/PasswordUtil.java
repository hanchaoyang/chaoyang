package com.chaoyang.example.util;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * 密码工具类
 *
 * @author 韩朝阳
 * @since 2023/5/25
 */
public class PasswordUtil {

    /**
     * 加密
     */
    public static String encrypt(String origin) {
        return DigestUtil.sha256Hex(DigestUtil.sha256Hex(origin));
    }

}