package com.chaoyang.example.entity.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 获取验证码请求参数
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Data
public class GetQrCodeRequest {

    /**
     * 临时字符串
     */
    @NotBlank(message = "临时字符串不能为空")
    @Length(min = 4, max = 32)
    private String nonce;

}