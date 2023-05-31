package com.chaoyang.example.entity.dto.response;

import com.chaoyang.example.constant.BasicConstant;
import com.chaoyang.example.entity.po.Permission;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 权限响应类
 *
 * @author 韩朝阳
 * @since 2023/3/26
 */
@Builder
@Getter
@Setter
public class PermissionResponse {

    /**
     * 权限ID
     */
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String code;

    /**
     * 是否是基础数据
     *
     * @see BasicConstant
     */
    private Integer basic;

    /**
     * PO转DTO
     */
    public static PermissionResponse of(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .code(permission.getCode())
                .build();
    }

}