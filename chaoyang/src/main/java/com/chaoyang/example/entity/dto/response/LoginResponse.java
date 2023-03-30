package com.chaoyang.example.entity.dto.response;

import lombok.Data;

/**
 * 登录响应类
 *
 * @author 韩朝阳
 * @since 2023/3/21
 */
@Data
public class LoginResponse {

    /**
     * Token
     */
    private String token;

}