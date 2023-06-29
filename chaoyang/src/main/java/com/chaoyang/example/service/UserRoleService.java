package com.chaoyang.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoyang.example.entity.dto.request.CreateUserRoleRequest;
import com.chaoyang.example.entity.dto.request.FindUserRolePageRequest;
import com.chaoyang.example.entity.dto.request.RemoveUserRoleRequest;
import com.chaoyang.example.entity.dto.response.UserRoleResponse;
import com.chaoyang.example.entity.po.UserRole;

import java.util.List;

/**
 * 用户角色关联服务层接口
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 根据用户ID和角色ID查询用户角色关联是否存在
     */
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 根据用户ID查询用户角色关联
     */
    List<UserRole> findByUserId(Long userId);

    /**
     * 根据用户ID和角色ID查询用户角色关联
     */
    UserRole findByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 根据用户ID分页查询用户角色关联
     */
    Page<UserRoleResponse> findPage(FindUserRolePageRequest request);

    /**
     * 添加用户角色关联
     */
    void create(CreateUserRoleRequest request);

    /**
     * 删除用户角色关联
     */
    void remove(RemoveUserRoleRequest request);

    /**
     * 根据用户ID删除用户角色关联
     */
    void removeByUserId(Long userId);

    /**
     * 根据角色ID删除用户角色关联
     */
    void removeByRoleId(Long roleId);

}