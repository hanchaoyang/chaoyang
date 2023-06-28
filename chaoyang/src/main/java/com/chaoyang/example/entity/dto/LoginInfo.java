package com.chaoyang.example.entity.dto;

import lombok.Data;

import java.util.Set;

/**
 * 登录信息
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Data
public class LoginInfo {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户角色集合
     */
    private Set<String> roles;

    /**
     * 用户权限集合
     */
    private Set<String> permissions;

}