package com.chaoyang.example.entity.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 添加用户请求参数类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Data
public class CreateUserRequest {

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    @Length(min = 1, max = 20)
    private String nickname;

    /**
     * 用户手机号
     */
    @NotBlank(message = "用户手机号不能为空")
    @Length(min = 11, max = 11)
    private String phone;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 6, max = 20)
    private String password;

}