package com.chaoyang.example.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 删除角色权限关联请求类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Data
public class RemoveRolePermissionRequest {

    /**
     * 角色权限关联ID
     */
    @NotNull(message = "角色权限关联ID不能为空")
    private Long id;

}