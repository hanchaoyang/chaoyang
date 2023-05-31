package com.chaoyang.example.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加用户角色关联请求类
 *
 * @author 韩朝阳
 * @since 2023/4/21
 */
@Data
public class CreateUserRoleRequest {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

}