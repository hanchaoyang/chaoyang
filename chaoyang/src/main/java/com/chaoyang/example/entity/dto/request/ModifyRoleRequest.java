package com.chaoyang.example.entity.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 修改角色请求类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Data
public class ModifyRoleRequest {

    /**
     * 角色主键
     */
    @NotNull(message = "角色主键不能为空")
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Length(min = 1, max = 20, message = "角色名称长度为1-20个字符")
    private String roleName;

    /**
     * 角色标识
     */
    @NotBlank(message = "角色标识不能为空")
    @Length(min = 1, max = 40, message = "角色标识长度为1-40个字符")
    private String roleCode;

}