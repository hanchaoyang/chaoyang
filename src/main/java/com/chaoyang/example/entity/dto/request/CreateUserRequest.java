package com.chaoyang.example.entity.dto.request;

import com.chaoyang.example.constant.UserStatusConstant;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加用户请求类
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
    @Length(min = 1, max = 20, message = "用户昵称长度为1-20个字符")
    private String userNickname;

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
     * 用户状态
     *
     * @see UserStatusConstant
     */
    @NotNull(message = "用户状态不能为空")
    private Integer userStatus;

}