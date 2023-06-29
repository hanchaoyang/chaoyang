package com.chaoyang.example.entity.dto.response;

import com.chaoyang.example.constant.BasicConstant;
import com.chaoyang.example.entity.po.Role;
import com.chaoyang.example.entity.po.User;
import com.chaoyang.example.entity.po.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;

/**
 * 用户角色关联响应类
 *
 * @author 韩朝阳
 * @since 2023/4/20
 */
@Builder
@Getter
@Setter
public class UserRoleResponse {

    /**
     * 用户角色关联ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

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
     * 是否是基础数据
     *
     * @see BasicConstant
     */
    private Integer basic;

    /**
     * PO转DTO
     */
    public static UserRoleResponse of(UserRole userRole, Map<Long, User> userMap, Map<Long, Role> roleMap) {
        UserRoleResponse response = UserRoleResponse.builder()
                .id(userRole.getId())
                .userId(userRole.getUserId())
                .roleId(userRole.getRoleId())
                .basic(userRole.getBasic())
                .build();

        if (Objects.nonNull(userMap)) {
            User user = userMap.get(userRole.getUserId());

            if (Objects.nonNull(user)) {
                response.setUserId(user.getId());
            }
        }

        if (Objects.nonNull(roleMap)) {
            Role role = roleMap.get(userRole.getRoleId());

            if (Objects.nonNull(role)) {
                response.setRoleName(role.getName());
                response.setRoleCode(role.getCode());
            }
        }

        return response;
    }

}