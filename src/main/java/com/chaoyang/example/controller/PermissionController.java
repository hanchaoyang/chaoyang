package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public Result<PermissionResponse> find(FindPermissionRequest findPermissionRequest) {
        PermissionResponse permissionResponse = this.permissionService.find(findPermissionRequest);

        return Result.success(permissionResponse);
    }

    @GetMapping("/permission/page")
    public Result<Page<PermissionResponse>> findPage(FindPermissionPageRequest findPermissionPageRequest) {
        Page<PermissionResponse> permissionPage = this.permissionService.findPage(findPermissionPageRequest);

        return Result.success(permissionPage);
    }

    @GetMapping("/permission/inactive/page")
    public Result<Page<PermissionResponse>> findInactivePage(FindInactivePermissionPageRequest findInactivePermissionPageRequest) {
        Page<PermissionResponse> inactivePermissionPage = this.permissionService.findInactivePage(findInactivePermissionPageRequest);

        return Result.success(inactivePermissionPage);
    }

    @PostMapping("/permission")
    public Result<Void> create(@RequestBody @Valid CreatePermissionRequest createPermissionRequest) {
        this.permissionService.create(createPermissionRequest);

        return Result.success();
    }

    @PutMapping("/permission")
    public Result<Void> modify(@RequestBody @Valid ModifyPermissionRequest modifyPermissionRequest) {
        this.permissionService.modify(modifyPermissionRequest);

        return Result.success();
    }

    @DeleteMapping("/permission")
    public Result<Void> remove(RemovePermissionRequest removePermissionRequest) {
        this.permissionService.remove(removePermissionRequest);

        return Result.success();
    }

}