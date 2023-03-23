package com.chaoyang.example.entity.dto.response;

import lombok.Data;

import java.util.Set;

/**
 * 登录信息响应类
 *
 * @author 韩朝阳
 * @since 2023/3/22
 */
@Data
public class LoginInfoResponse {

    private Long userId;

    private String userNickname;

    private String userPhone;

    private Set<String> userRoles;

    private Set<String> userPermissions;

}