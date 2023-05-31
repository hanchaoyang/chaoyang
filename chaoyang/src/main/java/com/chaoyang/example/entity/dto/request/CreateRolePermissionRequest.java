package com.chaoyang.example.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加角色权限关联请求类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Data
public class CreateRolePermissionRequest {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    /**
     * 权限ID
     */
    @NotNull(message = "权限ID不能为空")
    private Long permissionId;

}