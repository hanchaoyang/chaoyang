package com.chaoyang.example.entity.dto.response;

import lombok.Data;

/**
 * 用户角色响应类
 *
 * @author 韩朝阳
 * @since 2023/4/20
 */
@Data
public class UserRoleResponse {

    /**
     * 用户角色主键
     */
    private Long userRoleId;

    /**
     * 用户主键
     */
    private Long userId;

    /**
     * 角色主键
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

}