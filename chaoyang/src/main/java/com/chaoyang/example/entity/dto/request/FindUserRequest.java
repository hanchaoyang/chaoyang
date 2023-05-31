package com.chaoyang.example.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 查询用户请求类
 *
 * @author 韩朝阳
 * @since 2023/3/28
 */
@Data
public class FindUserRequest {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long id;

}