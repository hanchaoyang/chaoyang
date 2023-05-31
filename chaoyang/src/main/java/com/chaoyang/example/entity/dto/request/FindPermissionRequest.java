package com.chaoyang.example.entity.dto.request;

import lombok.Data;

/**
 * 查询权限请求类
 *
 * @author 韩朝阳
 * @since 2023/3/26
 */
@Data
public class FindPermissionRequest {

    /**
     * 权限ID
     */
    private Long id;

}