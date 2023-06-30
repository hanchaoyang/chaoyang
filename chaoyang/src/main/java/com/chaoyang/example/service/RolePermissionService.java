package com.chaoyang.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoyang.example.entity.dto.request.CreateRolePermissionRequest;
import com.chaoyang.example.entity.dto.request.FindRolePermissionPageRequest;
import com.chaoyang.example.entity.dto.request.RemoveRolePermissionRequest;
import com.chaoyang.example.entity.dto.response.RolePermissionResponse;
import com.chaoyang.example.entity.po.RolePermission;

import java.util.List;

/**
 * 角色权限关联服务层接口
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 根据角色ID和权限ID查询角色权限关联是否存在
     */
    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

    /**
     * 根据角色ID和权限ID查询角色权限关联
     */
    RolePermission findByRoleIdAndPermissionId(Long roleId, Long permissionId);

    /**
     * 根据角色ID查询角色权限关联
     */
    List<RolePermission> findByRoleId(Long roleId);

    /**
     * 根据角色ID集合查询角色权限关联
     */
    List<RolePermission> findByRoleIds(List<Long> roleIds);

    /**
     * 根据角色ID分页查询角色权限关联
     */
    Page<RolePermissionResponse> findPage(FindRolePermissionPageRequest request);

    /**
     * 添加角色权限关联
     */
    void create(CreateRolePermissionRequest request);

    /**
     * 删除角色权限关联
     */
    void remove(RemoveRolePermissionRequest request);

    /**
     * 根据角色ID删除角色权限关联
     */
    void removeByRoleId(Long roleId);

    /**
     * 根据权限ID删除角色权限关联
     */
    void removeByPermissionId(Long permissionId);

}