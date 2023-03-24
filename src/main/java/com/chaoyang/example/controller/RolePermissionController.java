package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.CreateRolePermissionRequest;
import com.chaoyang.example.entity.dto.request.FindRolePermissionPageRequest;
import com.chaoyang.example.entity.dto.response.FindRolePermissionPageResponse;
import com.chaoyang.example.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 角色权限控制层
 *
 * @author 韩朝阳
 * @since 2023/3/23
 */
@RestController
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    @GetMapping("/role-permission/page")
    public Result<Page<FindRolePermissionPageResponse>> findRolePermissionPage(FindRolePermissionPageRequest findRolePermissionPageRequest) {
        Page<FindRolePermissionPageResponse> findRolePermissionPageResponsePage = this.rolePermissionService.findRolePermissionPage(findRolePermissionPageRequest);

        return Result.success(findRolePermissionPageResponsePage);
    }

    @PostMapping("/role-permission")
    public Result<Void> create(@RequestBody @Valid CreateRolePermissionRequest createRolePermissionRequest) {
        this.rolePermissionService.create(createRolePermissionRequest);

        return Result.success();
    }

}