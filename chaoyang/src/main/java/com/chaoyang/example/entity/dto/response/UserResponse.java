package com.chaoyang.example.entity.dto.response;

import com.chaoyang.example.constant.UserStatusConstant;
import com.chaoyang.example.entity.po.User;
import lombok.Data;

/**
 * 用户响应类
 *
 * @author 韩朝阳
 * @since 2023/3/28
 */
@Data
public class UserResponse {

    /**
     * 用户主键
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 用户手机号
     */
    private String userPhone;

//    /**
//     * 用户密码
//     */
//    private String userPassword;

    /**
     * 用户状态
     *
     * @see UserStatusConstant
     */
    private Integer userStatus;

    /**
     * 用户状态名称
     */
    private String userStatusName;

    public static UserResponse of(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setUserId(user.getId());
        userResponse.setUserNickname(user.getNickname());
        userResponse.setUserPhone(user.getPhone());
        userResponse.setUserStatus(user.getStatus());

        switch (user.getStatus()) {
            case UserStatusConstant.DISABLE:
                userResponse.setUserStatusName("禁用");

                break;
            case UserStatusConstant.ENABLE:
                userResponse.setUserStatusName("启用");

                break;
            default:
        }

        return userResponse;
    }

}