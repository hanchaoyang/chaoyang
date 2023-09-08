package com.chaoyang.example.controller;

import com.chaoyang.example.annotation.RequiredPermission;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.CreateRolePermissionRequest;
import com.chaoyang.example.entity.dto.request.RemoveRolePermissionRequest;
import com.chaoyang.example.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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