package com.chaoyang.example.entity.dto.response;

import com.chaoyang.example.constant.BasicConstant;
import com.chaoyang.example.entity.po.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色响应类
 *
 * @author 韩朝阳
 * @since 2023/3/23
 */
@Builder
@Getter
@Setter
public class RoleResponse {

    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色标识
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
    public static RoleResponse of(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .code(role.getCode())
                .basic(role.getBasic())
                .build();
    }

}