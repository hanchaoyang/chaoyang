package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.annotation.RequiredPermission;
import com.chaoyang.example.constant.UserStatusConstant;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.UserResponse;
import com.chaoyang.example.exception.ParameterException;
import com.chaoyang.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 用户控制层
 *
 * @author 韩朝阳
 * @since 2023/3/20
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    @RequiredPermission("user:find")
    public Result<UserResponse> find(@Valid FindUserRequest request) {
        UserResponse response = this.userService.find(request);

        return Result.success(response);
    }

    @GetMapping("/user/page")
    @RequiredPermission("user:find")
    public Result<Page<UserResponse>> findPage(@Valid FindUserPageRequest request) {
        /*
         * 校验状态值是否合法
         */
        if (Objects.nonNull(request.getStatus()) && !UserStatusConstant.isValid(request.getStatus())) {
            throw new ParameterException(ParameterException.Message.USER_STATUS_ERROR);
        }

        Page<UserResponse> page = this.userService.findPage(request);

        return Result.success(page);
    }

    @PostMapping("/user")
    @RequiredPermission("user:create")
    public Result<Void> create(@RequestBody @Valid CreateUserRequest request) {
        /*
         * 校验状态值是否合法
         */
        if (!UserStatusConstant.isValid(request.getStatus())) {
            throw new ParameterException(ParameterException.Message.USER_STATUS_ERROR);
        }

        this.userService.create(request);

        return Result.success();
    }

    @PutMapping("/user")
    @RequiredPermission("user:modify")
    public Result<Void> modify(@RequestBody @Valid ModifyUserRequest request) {
        this.userService.modify(request);

        return Result.success();
    }

    @PatchMapping("/user/status")
    @RequiredPermission("user:modify")
    public Result<Void> modifyUserStatus(@RequestBody @Valid ModifyUserStatusRequest request) {
        /*
         * 校验状态值是否合法
         */
        if (!UserStatusConstant.isValid(request.getStatus())) {
            throw new ParameterException(ParameterException.Message.USER_STATUS_ERROR);
        }

        this.userService.modifyStatus(request);

        return Result.success();
    }

    @PatchMapping("/user/password")
    @RequiredPermission("user:modify")
    public Result<Void> modifyUserPassword(@RequestBody @Valid ModifyUserPasswordRequest request) {
        this.userService.modifyPassword(request);

        return Result.success();
    }

    @DeleteMapping("/user")
    @RequiredPermission("user:remove")
    public Result<Void> remove(@Valid RemoveUserRequest request) {
        this.userService.remove(request);

        return Result.success();
    }

}