package com.chaoyang.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.entity.dto.Result;
import com.chaoyang.example.entity.dto.request.CreateUserRequest;
import com.chaoyang.example.entity.dto.request.FindUserPageRequest;
import com.chaoyang.example.entity.dto.request.FindUserRequest;
import com.chaoyang.example.entity.dto.request.ModifyUserRequest;
import com.chaoyang.example.entity.dto.response.UserResponse;
import com.chaoyang.example.entity.po.User;
import com.chaoyang.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public Result<UserResponse> find(FindUserRequest findUserRequest) {
        UserResponse userResponse = this.userService.find(findUserRequest);

        return Result.success(userResponse);
    }

    @GetMapping("/user/page")
    public Result<Page<UserResponse>> findPage(FindUserPageRequest findUserPageRequest) {
        Page<UserResponse> userResponsePage = this.userService.findPage(findUserPageRequest);

        return Result.success(userResponsePage);
    }

    @PostMapping("/user")
    public Result<Void> create(@RequestBody @Valid CreateUserRequest createUserRequest) {
        this.userService.create(createUserRequest);

        return Result.success();
    }

    @PutMapping("/user")
    public Result<Void> create(@RequestBody @Valid ModifyUserRequest modifyUserRequest) {
        this.userService.modify(modifyUserRequest);

        return Result.success();
    }

}