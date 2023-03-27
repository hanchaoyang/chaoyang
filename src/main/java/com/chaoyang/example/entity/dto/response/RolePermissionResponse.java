package com.chaoyang.example.entity.dto.response;

import lombok.Data;

/**
 * 角色权限响应类
 *
 * @author 韩朝阳
 * @since 2023/3/23
 */
@Data
public class RolePermissionResponse {

    /**
     * 角色权限主键
     */
    private Long rolePermissionId;
//
//    /**
//     * 角色主键
//     */
//    private Long roleId;
//
//    /**
//     * 角色名称
//     */
//    private String roleName;
//
//    /**
//     * 角色标识
//     */
//    private String roleCode;

    /**
     * 权限主键
     */
    private Long permissionId;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限标识
     */
    private String permissionCode;

}