package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.annotation.RequiredPermission;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.PermissionResponse;
import com.chaoyang.example.entity.dto.response.RoleResponse;
import com.chaoyang.example.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 角色控制层
 *
 * @author 韩朝阳
 * @since 2023/3/23
 */
@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/role")
    @RequiredPermission("role:find")
    public Result<RoleResponse> find(@Valid FindRoleRequest request) {
        RoleResponse response = this.roleService.find(request);

        return Result.success(response);
    }

    @GetMapping("/role/page")
    @RequiredPermission("role:find")
    public Result<Page<RoleResponse>> findPage(@Valid FindRolePageRequest request) {
        Page<RoleResponse> page = this.roleService.findPage(request);

        return Result.success(page);
    }

    @GetMapping("/role/inactive/page")
    @RequiredPermission(value = {"role:find", "user:find"}, and = true)
    public Result<Page<RoleResponse>> findInactivePage(@Valid FindInactiveRolePageRequest request) {
        Page<RoleResponse> page = this.roleService.findInactivePage(request);

        return Result.success(page);
    }

    @PostMapping("/role")
    @RequiredPermission("role:create")
    public Result<Void> create(@RequestBody @Valid CreateRoleRequest request) {
        this.roleService.create(request);

        return Result.success();
    }

    @PutMapping("/role")
    @RequiredPermission("role:modify")
    public Result<Void> modify(@RequestBody @Valid ModifyRoleRequest request) {
        this.roleService.modify(request);

        return Result.success();
    }

    @DeleteMapping("/role")
    @RequiredPermission("role:remove")
    public Result<Void> remove(@Valid RemoveRoleRequest request) {
        this.roleService.remove(request);

        return Result.success();
    }

}