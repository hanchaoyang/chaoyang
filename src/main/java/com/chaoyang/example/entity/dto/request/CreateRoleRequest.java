package com.chaoyang.example.entity.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 添加角色请求参数类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Data
public class CreateRoleRequest {

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Length(min = 1, max = 20)
    private String roleName;

    /**
     * 角色标识
     */
    @NotBlank(message = "角色标识不能为空")
    @Length(min = 1, max = 40)
    private String roleCode;

}