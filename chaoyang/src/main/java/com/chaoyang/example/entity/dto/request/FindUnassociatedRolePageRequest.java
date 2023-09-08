package com.chaoyang.example.entity.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 分页查询用户未关联的角色请求类
 *
 * @author 韩朝阳
 * @since 2023/4/4
 */
@Data
public class FindUnassociatedRolePageRequest {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    @Range(min = 1L, max = 100000000L, message = "页码为1-100000000")
    private Long current;

    /**
     * 页面大小
     */
    @NotNull(message = "页面大小不能为空")
    @Range(min = 1L, max = 1000L, message = "页面大小为1-10000")
    private Long size;

}