package com.chaoyang.example.entity.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 登录请求类
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Data
public class LoginRequest {

    /**
     * 用户账号
     */
    @NotBlank(message = "用户账号不能为空")
    @Length(min = 4, max = 20, message = "用户账号长度为4-20个字符")
    private String account;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 6, max = 20, message = "用户密码长度为6-20个字符")
    private String password;

    /**
     * 临时字符串
     */
    @NotBlank(message = "临时字符串不能为空")
    @Length(min = 4, max = 32, message = "临时字符串长度为4-32个字符")
    private String nonce;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Length(min = 1, max = 4, message = "验证码长度为1-4个字符")
    private String captcha;

}