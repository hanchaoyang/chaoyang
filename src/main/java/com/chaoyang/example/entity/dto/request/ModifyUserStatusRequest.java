package com.chaoyang.example.entity.dto.request;

import com.chaoyang.example.constant.UserStatusConstant;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 修改用户状态请求参数类
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Data
public class ModifyUserStatusRequest {

    /**
     * 用户主键
     */
    @NotNull(message = "用户主键不能为空")
    private Long userId;

    /**
     * 用户状态
     *
     * @see UserStatusConstant
     */
    @NotNull(message = "用户状态不能为空")
    private Integer userStatus;

}