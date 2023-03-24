package com.chaoyang.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoyang.example.entity.dto.request.CreateRolePermissionRequest;
import com.chaoyang.example.entity.dto.request.FindRolePermissionPageRequest;
import com.chaoyang.example.entity.dto.request.RemoveRolePermissionRequest;
import com.chaoyang.example.entity.dto.response.FindRolePermissionPageResponse;
import com.chaoyang.example.entity.po.RolePermission;

import java.util.List;

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
     * 根据角色主键和权限主键查询角色权限是否存在
     */
    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

    /**
     * 根据角色主键查询角色权限
     */
    List<RolePermission> findByRoleId(Long roleId);

    /**
     * 根据角色主键集合查询角色权限
     */
    List<RolePermission> findByRoleIds(List<Long> roleIds);

    /**
     * 根据角色主键分页查询角色权限
     */
    Page<FindRolePermissionPageResponse> findRolePermissionPage(FindRolePermissionPageRequest findRolePermissionPageRequest);

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