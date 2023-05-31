package com.chaoyang.example.entity.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 分页查询角色未关联的权限请求类
 *
 * @author 韩朝阳
 * @since 2023/3/23
 */
@Data
public class FindInactivePermissionPageRequest {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

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