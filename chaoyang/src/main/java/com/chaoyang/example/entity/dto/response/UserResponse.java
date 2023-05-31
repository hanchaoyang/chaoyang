package com.chaoyang.example.entity.dto.response;

import com.chaoyang.example.constant.BasicConstant;
import com.chaoyang.example.constant.UserStatusConstant;
import com.chaoyang.example.entity.po.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户响应类
 *
 * @author 韩朝阳
 * @since 2023/3/28
 */
@Builder
@Getter
@Setter
public class UserResponse {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户状态
     *
     * @see UserStatusConstant
     */
    private Integer status;

    /**
     * 用户状态名称
     */
    private String statusName;

    /**
     * 是否是基础数据
     *
     * @see BasicConstant
     */
    private Integer basic;

    /**
     * PO转DTO
     */
    public static UserResponse of(User user) {
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .account(user.getAccount())
                .status(user.getStatus())
                .basic(user.getBasic())
                .build();

        switch (user.getStatus()) {
            case UserStatusConstant.DISABLE:
                response.setStatusName("禁用");

                break;
            case UserStatusConstant.ENABLE:
                response.setStatusName("启用");

                break;
            default:
        }

        return response;
    }

}