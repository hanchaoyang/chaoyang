package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.FindRolePageResponse;
import com.chaoyang.example.entity.dto.response.FindRoleResponse;
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
    public Result<FindRoleResponse> findRole(FindRoleRequest findRoleRequest) {
        FindRoleResponse findRoleResponse = this.roleService.findRoleResponse(findRoleRequest);

        return Result.success(findRoleResponse);
    }

    @GetMapping("/role/page")
    public Result<Page<FindRolePageResponse>> findRolePage(FindRolePageRequest findRolePageRequest) {
        Page<FindRolePageResponse> findRolePageResponsePage = this.roleService.findRolePage(findRolePageRequest);

        return Result.success(findRolePageResponsePage);
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