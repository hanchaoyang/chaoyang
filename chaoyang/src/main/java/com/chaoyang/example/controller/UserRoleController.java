package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.annotation.RequiredPermission;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.CreateUserRoleRequest;
import com.chaoyang.example.entity.dto.request.FindUserRolePageRequest;
import com.chaoyang.example.entity.dto.request.RemoveUserRoleRequest;
import com.chaoyang.example.entity.dto.response.UserRoleResponse;
import com.chaoyang.example.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户角色关联控制层
 *
 * @author 韩朝阳
 * @since 2023/4/20
 */
@RestController
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @GetMapping("/user-role/page")
    @RequiredPermission(value = {"user:find", "role:find"}, and = true)
    public Result<Page<UserRoleResponse>> findPage(@Valid FindUserRolePageRequest request) {
        Page<UserRoleResponse> page = this.userRoleService.findPage(request);

        return Result.success(page);
    }

    @PostMapping("/user-role")
    @RequiredPermission(value = {"user:find", "user:modify", "role:find"}, and = true)
    public Result<Void> create(@RequestBody @Valid CreateUserRoleRequest request) {
        this.userRoleService.create(request);

        return Result.success();
    }

    @DeleteMapping("/user-role")
    @RequiredPermission(value = {"user:find", "user:modify", "role:find"}, and = true)
    public Result<Void> remove(@Valid RemoveUserRoleRequest request) {
        this.userRoleService.remove(request);

        return Result.success();
    }

}