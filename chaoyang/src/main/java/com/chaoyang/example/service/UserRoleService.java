package com.chaoyang.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.RolePermissionResponse;
import com.chaoyang.example.entity.dto.response.UserRoleResponse;
import com.chaoyang.example.entity.po.Role;
import com.chaoyang.example.entity.po.UserRole;

import java.util.List;
import java.util.Set;

/**
 * 用户角色服务层接口
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
public interface UserRoleService extends IService<UserRole> {

    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 根据主键查询用户角色是否存在
     */
    boolean existsById(Long id);

    /**
     * 根据主键查询用户角色是否不存在
     */
    boolean notExistsById(Long id);

    /**
     * 根据用户主键查询角色
     */
    List<UserRole> findByUserId(Long userId);

    /**
     * 根据用户主键分页查询用户角色
     */
    Page<UserRoleResponse> findPage(FindUserRolePageRequest findUserRolePageRequest);

    /**
     * 添加用户角色
     */
    void create(CreateUserRoleRequest createUserRoleRequest);

    /**
     * 删除用户角色
     */
    void remove(RemoveUserRoleRequest removeUserRoleRequest);

    void removeByUserId(Long userId);

    void removeByRoleId(Long roleId);

}