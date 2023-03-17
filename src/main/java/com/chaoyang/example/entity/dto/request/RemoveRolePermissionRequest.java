package com.chaoyang.example.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 删除角色权限请求参数类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Data
public class RemoveRolePermissionRequest {

    /**
     * 角色权限主键
     */
    @NotNull(message = "角色权限主键不能为空")
    private Long rolePermissionId;

}