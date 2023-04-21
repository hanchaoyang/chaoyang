package com.chaoyang.example.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加用户角色请求类
 *
 * @author 韩朝阳
 * @since 2023/4/21
 */
@Data
public class CreateUserRoleRequest {

    /**
     * 用户主键
     */
    @NotNull(message = "用户主键不能为空")
    private Long userId;

    /**
     * 角色主键
     */
    @NotNull(message = "角色主键不能为空")
    private Long roleId;

}