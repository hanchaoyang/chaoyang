package com.chaoyang.example.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 删除用户角色关联请求类
 *
 * @author 韩朝阳
 * @since 2023/4/20
 */
@Data
public class RemoveUserRoleRequest {

    /**
     * 用户角色关联ID
     */
    @NotNull(message = "用户角色关联ID不能为空")
    private Long id;

}