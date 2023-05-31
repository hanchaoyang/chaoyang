package com.chaoyang.example.entity.dto.response;

import com.chaoyang.example.constant.BasicConstant;
import com.chaoyang.example.entity.po.Permission;
import com.chaoyang.example.entity.po.Role;
import com.chaoyang.example.entity.po.RolePermission;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;

/**
 * 角色权限关联响应类
 *
 * @author 韩朝阳
 * @since 2023/3/23
 */
@Builder
@Getter
@Setter
public class RolePermissionResponse {

    /**
     * 角色权限关联ID
     */
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色标识
     */
    private String roleCode;

    /**
     * 权限ID
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

    /**
     * 是否是基础数据
     *
     * @see BasicConstant
     */
    private Integer basic;

    /**
     * PO转DTO
     */
    public static RolePermissionResponse of(RolePermission rolePermission, Map<Long, Role> roleMap, Map<Long, Permission> permissionMap) {
        RolePermissionResponse response = RolePermissionResponse.builder()
                .id(rolePermission.getId())
                .roleId(rolePermission.getRoleId())
                .permissionId(rolePermission.getPermissionId())
                .build();

        if (Objects.nonNull(roleMap)) {
            Role role = roleMap.get(rolePermission.getRoleId());

            if (Objects.nonNull(role)) {
                response.setRoleName(role.getName());
                response.setRoleCode(role.getCode());
            }
        }

        if (Objects.nonNull(permissionMap)) {
            Permission permission = permissionMap.get(rolePermission.getPermissionId());

            if (Objects.nonNull(permission)) {
                response.setPermissionName(permission.getName());
                response.setPermissionCode(permission.getCode());
            }
        }

        return response;
    }

}