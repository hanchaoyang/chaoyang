package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.*;
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
    public Result<RoleResponse> find(FindRoleRequest findRoleRequest) {
        RoleResponse roleResponse = this.roleService.find(findRoleRequest);

        return Result.success(roleResponse);
    }

    @GetMapping("/role/page")
    public Result<Page<RoleResponse>> findPage(FindRolePageRequest findRolePageRequest) {
        Page<RoleResponse> roleResponsePage = this.roleService.findPage(findRolePageRequest);

        return Result.success(roleResponsePage);
    }

    @PostMapping("/role")
    public Result<Void> create(@RequestBody @Valid CreateRoleRequest createRoleRequest) {
        this.roleService.create(createRoleRequest);

        return Result.success();
    }

    @PutMapping("/role")
    public Result<Void> modify(@RequestBody @Valid ModifyRoleRequest modifyRoleRequest) {
        this.roleService.modify(modifyRoleRequest);

        return Result.success();
    }

    @DeleteMapping("/role")
    public Result<Void> remove(RemoveRoleRequest removeRoleRequest) {
        this.roleService.remove(removeRoleRequest);

        return Result.success();
    }

}