package com.chaoyang.example.entity.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 修改用户请求参数类
 *
 * @author 韩朝阳
 * @since 2023/3/18
 */
@Data
public class ModifyUserRequest {

    /**
     * 用户主键
     */
    @NotNull(message = "用户主键不能为空")
    private Long userId;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    @Length(min = 1, max = 20)
    private String userNickname;

    /**
     * 用户手机号
     */
    @NotBlank(message = "用户手机号不能为空")
    @Length(min = 11, max = 11)
    private String userPhone;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 6, max = 20)
    private String userPassword;

}