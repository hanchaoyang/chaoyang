package com.chaoyang.example.entity.dto.request;

import com.chaoyang.example.constant.UserStatusConstant;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 修改用户密码请求类
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Data
public class ModifyUserPasswordRequest {

    /**
     * 用户主键
     */
    @NotNull(message = "用户主键不能为空")
    private Long userId;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 6, max = 20, message = "用户密码长度为6-20个字符")
    private String userPassword;

}