package com.chaoyang.example.entity.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 分页查询权限请求类
 *
 * @author 韩朝阳
 * @since 2023/3/23
 */
@Data
public class FindPermissionPageRequest {

    /**
     * 权限名称
     */
    @Length(min = 1, max = 20, message = "权限名称长度为1-20个字符")
    private String name;

    /**
     * 权限标识
     */
    @Length(min = 1, max = 40, message = "权限标识长度为1-40个字符")
    private String code;

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