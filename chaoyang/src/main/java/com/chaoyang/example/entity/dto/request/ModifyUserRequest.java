package com.chaoyang.example.entity.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 修改用户请求类
 *
 * @author 韩朝阳
 * @since 2023/3/18
 */
@Data
public class ModifyUserRequest {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long id;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    @Length(min = 1, max = 20, message = "用户昵称长度为1-20个字符")
    private String nickname;

    /**
     * 用户账号
     */
    @NotBlank(message = "用户账号不能为空")
    @Length(min = 4, max = 40, message = "用户账号长度为4-40个字符")
    private String account;

}