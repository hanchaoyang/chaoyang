package com.chaoyang.example.controller;

import com.chaoyang.example.entity.dto.request.CreateUserRequest;
import com.chaoyang.example.service.UserService;
import com.hanchaoyang.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/user")
    public Result<Void> create(@RequestBody @Valid CreateUserRequest createUserRequest) {
        this.userService.create(createUserRequest);

        return Result.success();
    }

}