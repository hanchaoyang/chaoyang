package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.FindRolePageRequest;
import com.chaoyang.example.entity.dto.request.RemoveRoleRequest;
import com.chaoyang.example.entity.dto.response.FindRolePageResponse;
import com.chaoyang.example.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/role/page")
    public Result<Page<FindRolePageResponse>> findRolePage(FindRolePageRequest findRolePageRequest) {
        Page<FindRolePageResponse> findRolePageResponsePage = this.roleService.findRolePage(findRolePageRequest);

        return Result.success(findRolePageResponsePage);
    }

    @DeleteMapping("/role")
    public Result<Void> remove(RemoveRoleRequest removeRoleRequest) {
        this.roleService.remove(removeRoleRequest);

        return Result.success();
    }

}