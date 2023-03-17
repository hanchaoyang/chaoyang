package com.chaoyang.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoyang.example.entity.dto.request.CreateRolePermissionRequest;
import com.chaoyang.example.entity.dto.request.RemoveRolePermissionRequest;
import com.chaoyang.example.entity.po.RolePermission;

/**
 * 角色权限服务层接口
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 根据主键查询角色权限是否存在
     */
    boolean existsById(Long id);

    /**
     * 根据主键查询角色权限是否存在
     */
    boolean notExistsById(Long id);

    /**
     * 添加角色权限
     */
    void create(CreateRolePermissionRequest createRolePermissionRequest);

    /**
     * 删除角色权限
     */
    void remove(RemoveRolePermissionRequest removeRolePermissionRequest);

    /**
     * 根据角色主键删除角色权限
     */
    void removeByRoleId(Long roleId);

    /**
     * 根据权限主键删除角色权限
     */
    void removeByPermissionId(Long permissionId);

}