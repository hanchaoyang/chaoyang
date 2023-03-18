package com.chaoyang.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoyang.example.entity.dto.request.CreateUserRequest;
import com.chaoyang.example.entity.dto.request.ModifyUserRequest;
import com.chaoyang.example.entity.dto.request.RemoveUserRequest;
import com.chaoyang.example.entity.po.User;

/**
 * 用户服务层接口
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
public interface UserService extends IService<User> {

    boolean existsById(Long id);

    boolean notExistsById(Long id);

    boolean existsByPhone(String phone);

    void create(CreateUserRequest createUserRequest);

    void modify(ModifyUserRequest modifyUserRequest);

    void remove(RemoveUserRequest removeUserRequest);

}