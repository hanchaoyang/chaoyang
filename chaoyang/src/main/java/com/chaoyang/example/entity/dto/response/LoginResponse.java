package com.chaoyang.example.entity.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录响应类
 *
 * @author 韩朝阳
 * @since 2023/3/21
 */
@Builder
@Getter
@Setter
public class LoginResponse {

    /**
     * Token
     */
    private String token;

}