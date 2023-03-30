package com.chaoyang.example.entity.dto.request;

import com.chaoyang.example.constant.UserStatusConstant;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 分页查询用户请求类
 *
 * @author 韩朝阳
 * @since 2023/3/28
 */
@Data
public class FindUserPageRequest {

    /**
     * 用户昵称
     */
    @Length(min = 1, max = 20, message = "用户昵称长度为1-20个字符")
    private String userNickname;

    /**
     * 用户手机号
     */
    @Length(min = 1, max = 11, message = "用户手机号长度为1-11个字符")
    private String userPhone;

    /**
     * 用户状态
     *
     * @see UserStatusConstant
     */
    private Integer userStatus;

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    private Long current;

    /**
     * 页面大小
     */
    @NotNull(message = "页面大小不能为空")
    @Range(min = 1L, max = 100L, message = "页面大小为1-100")
    private Long size;

}