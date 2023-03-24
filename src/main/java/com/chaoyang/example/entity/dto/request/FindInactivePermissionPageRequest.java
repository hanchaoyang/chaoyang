package com.chaoyang.example.entity.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 分页查询未使用的权限请求类
 *
 * @author 韩朝阳
 * @since 2023/3/23
 */
@Data
public class FindInactivePermissionPageRequest {

    /**
     * 角色主键
     */
    @NotNull(message = "角色主键不能为空")
    private Long roleId;

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    private Long current;

    /**
     * 页面大小
     */
    @NotNull(message = "页面大小不能为空")
    @Range(min = 1L, max = 100L, message = "页面大小为1-100")
    private Long size;

}