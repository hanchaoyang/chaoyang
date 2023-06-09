package com.chaoyang.example.entity.dto.request;

import com.chaoyang.example.constant.UserStatusConstant;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 修改用户状态请求类
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Data
public class ModifyUserStatusRequest {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long id;

    /**
     * 用户状态
     *
     * @see UserStatusConstant
     */
    @NotNull(message = "用户状态不能为空")
    private Integer status;

}