package com.chaoyang.example.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加角色权限请求类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Data
public class CreateRolePermissionRequest {

    /**
     * 角色主键
     */
    @NotNull(message = "角色主键不能为空")
    private Long roleId;

    /**
     * 权限主键
     */
    @NotNull(message = "权限主键不能为空")
    private Long permissionId;

}