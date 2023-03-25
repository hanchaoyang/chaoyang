package com.chaoyang.example.entity.dto.response;

import lombok.Data;

/**
 * 角色响应类
 *
 * @author 韩朝阳
 * @since 2023/3/23
 */
@Data
public class FindRoleResponse {

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