package com.chaoyang.example.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 删除角色请求类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Data
public class RemoveRoleRequest {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    private Long id;

}