package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.FindInactivePermissionPageRequest;
import com.chaoyang.example.entity.dto.response.InactivePermissionResponse;
import com.chaoyang.example.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限控制层
 *
 * @author 韩朝阳
 * @since 2023/3/23
 */
@RestController
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping("/permission/inactive/page")
    public Result<Page<InactivePermissionResponse>> findInactivePermissionPage(FindInactivePermissionPageRequest findInactivePermissionPageRequest) {
        Page<InactivePermissionResponse> inactivePermissionPage = this.permissionService.findInactivePermissionPage(findInactivePermissionPageRequest);

        return Result.success(inactivePermissionPage);
    }

}