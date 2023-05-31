package com.chaoyang.example.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 删除权限请求类
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@Data
public class RemovePermissionRequest {

    /**
     * 权限ID
     */
    @NotNull(message = "权限ID不能为空")
    private Long id;

}