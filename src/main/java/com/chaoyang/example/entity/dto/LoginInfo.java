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

    private Long userId;

    private String userNickname;

    private String userPhone;

    private Set<String> userRoles;

    private Set<String> userPermissions;

}