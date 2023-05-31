package com.chaoyang.example.entity.dto.response;

import com.chaoyang.example.entity.dto.LoginInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 登录信息响应类
 *
 * @author 韩朝阳
 * @since 2023/3/22
 */
@Builder
@Getter
@Setter
public class LoginInfoResponse {

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

    /**
     * PO（Redis）转DTO
     */
    public static LoginInfoResponse of(LoginInfo loginInfo) {
        return LoginInfoResponse.builder()
                .userId(loginInfo.getUserId())
                .nickname(loginInfo.getNickname())
                .account(loginInfo.getAccount())
                .roles(loginInfo.getRoles())
                .permissions(loginInfo.getPermissions())
                .build();
    }

}