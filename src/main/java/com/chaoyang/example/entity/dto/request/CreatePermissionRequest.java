package com.chaoyang.example.entity.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 添加权限请求类
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@Data
public class CreatePermissionRequest {

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    @Length(min = 1, max = 20, message = "权限名称长度为1-20个字符")
    private String permissionName;

    /**
     * 权限标识
     */
    @NotBlank(message = "权限标识不能为空")
    @Length(min = 1, max = 40, message = "权限标识长度为1-40个字符")
    private String permissionCode;

}