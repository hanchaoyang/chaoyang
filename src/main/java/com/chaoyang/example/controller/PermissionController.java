package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.CreatePermissionRequest;
import com.chaoyang.example.entity.dto.request.FindInactivePermissionPageRequest;
import com.chaoyang.example.entity.dto.request.FindPermissionRequest;
import com.chaoyang.example.entity.dto.response.PermissionResponse;
import com.chaoyang.example.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @GetMapping("/permission")
    public Result<PermissionResponse> findPermission(FindPermissionRequest findPermissionRequest) {
        PermissionResponse permissionResponse = this.permissionService.find(findPermissionRequest);

        return Result.success(permissionResponse);
    }

    @GetMapping("/permission/inactive/page")
    public Result<Page<PermissionResponse>> findInactivePermissionPage(FindInactivePermissionPageRequest findInactivePermissionPageRequest) {
        Page<PermissionResponse> inactivePermissionPage = this.permissionService.findInactivePage(findInactivePermissionPageRequest);

        return Result.success(inactivePermissionPage);
    }

    @PostMapping("/permission")
    public Result<Void> create(@RequestBody @Valid CreatePermissionRequest createPermissionRequest) {
        this.permissionService.create(createPermissionRequest);

        return Result.success();
    }

}