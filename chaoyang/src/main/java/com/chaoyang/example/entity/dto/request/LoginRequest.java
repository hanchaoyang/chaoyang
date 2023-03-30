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
     * 用户手机号
     */
    @NotBlank(message = "用户手机号不能为空")
    @Length(min = 11, max = 11, message = "用户手机号长度为11个字符")
    private String userPhone;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 6, max = 20, message = "用户密码长度为6-20个字符")
    private String userPassword;

    /**
     * 临时字符串
     */
//    @NotBlank(message = "临时字符串不能为空")
//    @Length(min = 4, max = 32, message = "临时字符串长度为4-32个字符")
    private String nonce;

    /**
     * 验证码
     */
//    @NotBlank(message = "验证码不能为空")
//    @Length(min = 4, max = 4, message = "验证码长度为4个字符")
    private String qrcode;

}