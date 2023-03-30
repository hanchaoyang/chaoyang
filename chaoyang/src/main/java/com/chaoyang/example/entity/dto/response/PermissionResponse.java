package com.chaoyang.example.entity.dto.response;

import lombok.Data;

/**
 * 权限响应类
 *
 * @author 韩朝阳
 * @since 2023/3/26
 */
@Data
public class PermissionResponse {

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