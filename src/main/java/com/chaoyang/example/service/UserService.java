package com.chaoyang.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.UserResponse;
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

    User findByPhoneAndPassword(String phone, String password);

    UserResponse find(FindUserRequest findUserRequest);

    Page<UserResponse> findPage(FindUserPageRequest findUserPageRequest);

    void create(CreateUserRequest createUserRequest);

    void modify(ModifyUserRequest modifyUserRequest);

    void modifyStatus(ModifyUserStatusRequest modifyUserStatusRequest);

    void remove(RemoveUserRequest removeUserRequest);

}