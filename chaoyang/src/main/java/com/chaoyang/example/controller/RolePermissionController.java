package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.annotation.RequiredPermission;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.CreateRolePermissionRequest;
import com.chaoyang.example.entity.dto.request.FindRolePermissionPageRequest;
import com.chaoyang.example.entity.dto.request.RemoveRolePermissionRequest;
import com.chaoyang.example.entity.dto.response.RolePermissionResponse;
import com.chaoyang.example.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 角色权限关联控制层
 *
 * @author 韩朝阳
 * @since 2023/3/23
 */
@RestController
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    @Deprecated
    @GetMapping("/role-permission/page")
    @RequiredPermission(value = {"role:find", "permission:find"}, and = true)
    public Result<Page<RolePermissionResponse>> findPage(@Valid FindRolePermissionPageRequest request) {
        Page<RolePermissionResponse> page = this.rolePermissionService.findPage(request);

        return Result.success(page);
    }

    @PostMapping("/role-permission")
    @RequiredPermission(value = {"role:find", "role:modify", "permission:find"}, and = true)
    public Result<Void> create(@RequestBody @Valid CreateRolePermissionRequest request) {
        this.rolePermissionService.create(request);

        return Result.success();
    }

    @DeleteMapping("/role-permission")
    @RequiredPermission(value = {"role:find", "role:modify", "permission:find"}, and = true)
    public Result<Void> remove(@Valid RemoveRolePermissionRequest request) {
        this.rolePermissionService.remove(request);

        return Result.success();
    }

}