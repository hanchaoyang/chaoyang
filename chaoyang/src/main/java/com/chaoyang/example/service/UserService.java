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

    /**
     * 根据账号查询用户是否存在
     */
    boolean existsByAccount(String account);

    /**
     * 根据ID查询用户是否不存在
     */
    boolean notExistsById(Long id);

    /**
     * 根据账号和密码查询用户
     */
    User findByAccountAndPassword(String account, String password);

    /**
     * 根据ID查询用户
     */
    UserResponse find(FindUserRequest request);

    /**
     * 根据条件分页查询用户
     */
    Page<UserResponse> findPage(FindUserPageRequest request);

    /**
     * 添加用户
     */
    void create(CreateUserRequest request);

    /**
     * 修改用户
     */
    void modify(ModifyUserRequest request);

    /**
     * 修改用户状态
     */
    void modifyStatus(ModifyUserStatusRequest request);

    /**
     * 修改用户密码
     */
    void modifyPassword(ModifyUserPasswordRequest request);

    /**
     * 删除用户
     */
    void remove(RemoveUserRequest request);

}