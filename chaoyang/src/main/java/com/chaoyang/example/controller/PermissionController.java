package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.annotation.RequiredPermission;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.PermissionResponse;
import com.chaoyang.example.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @RequiredPermission("permission:find")
    public Result<PermissionResponse> find(@Valid FindPermissionRequest request) {
        PermissionResponse response = this.permissionService.find(request);

        return Result.success(response);
    }

    @GetMapping("/permission/page")
    @RequiredPermission("permission:find")
    public Result<Page<PermissionResponse>> findPage(@Valid FindPermissionPageRequest request) {
        Page<PermissionResponse> page = this.permissionService.findPage(request);

        return Result.success(page);
    }

    @GetMapping("/permission/active/page")
    @RequiredPermission(value = {"permission:find", "role:find"}, and = true)
    public Result<Page<PermissionResponse>> findActivePage(@Valid FindActivePermissionPageRequest request) {
        Page<PermissionResponse> page = this.permissionService.findActivePage(request);

        return Result.success(page);
    }

    @GetMapping("/permission/inactive/page")
    @RequiredPermission(value = {"permission:find", "role:find"}, and = true)
    public Result<Page<PermissionResponse>> findInactivePage(@Valid FindInactivePermissionPageRequest request) {
        Page<PermissionResponse> page = this.permissionService.findInactivePage(request);

        return Result.success(page);
    }

    @PostMapping("/permission")
    @RequiredPermission("permission:create")
    public Result<Void> create(@RequestBody @Valid CreatePermissionRequest request) {
        this.permissionService.create(request);

        return Result.success();
    }

    @PutMapping("/permission")
    @RequiredPermission("permission:modify")
    public Result<Void> modify(@RequestBody @Valid ModifyPermissionRequest request) {
        this.permissionService.modify(request);

        return Result.success();
    }

    @DeleteMapping("/permission")
    @RequiredPermission("permission:remove")
    public Result<Void> remove(@Valid RemovePermissionRequest request) {
        this.permissionService.remove(request);

        return Result.success();
    }

}