package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.RolePermissionResponse;
import com.chaoyang.example.entity.dto.response.UserRoleResponse;
import com.chaoyang.example.entity.po.UserRole;
import com.chaoyang.example.service.RolePermissionService;
import com.chaoyang.example.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户角色控制层
 *
 * @author 韩朝阳
 * @since 2023/4/20
 */
@RestController
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @GetMapping("/user-role/page")
    public Result<Page<UserRoleResponse>> findPage(FindUserRolePageRequest findUserRolePageRequest) {
        Page<UserRoleResponse> userRoleResponsePage = this.userRoleService.findPage(findUserRolePageRequest);

        return Result.success(userRoleResponsePage);
    }

    @PostMapping("/user-role")
    public Result<Void> create(@RequestBody @Valid CreateUserRoleRequest createUserRoleRequest) {
        this.userRoleService.create(createUserRoleRequest);

        return Result.success();
    }

    @DeleteMapping("/user-role")
    public Result<Void> remove(RemoveUserRoleRequest removeUserRoleRequest) {
        this.userRoleService.remove(removeUserRoleRequest);

        return Result.success();
    }

}