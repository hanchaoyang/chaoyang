package com.chaoyang.example.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 删除用户请求类
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Data
public class RemoveUserRequest {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long id;

}