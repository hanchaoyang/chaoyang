package com.chaoyang.example.entity.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 修改权限请求参数类
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@Data
public class ModifyPermissionRequest {

    /**
     * 权限主键
     */
    @NotNull(message = "权限主键不能为空")
    private Long id;

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    @Length(min = 1, max = 20)
    private String permissionName;

    /**
     * 权限标识
     */
    @NotBlank(message = "权限标识不能为空")
    @Length(min = 1, max = 40)
    private String permissionCode;

}