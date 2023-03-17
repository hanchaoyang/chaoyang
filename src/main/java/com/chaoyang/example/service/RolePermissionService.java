package com.chaoyang.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoyang.example.entity.po.RolePermission;

/**
 * 角色权限服务层接口
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 根据角色主键删除角色权限
     */
    void removeByRoleId(Long roleId);

    /**
     * 根据权限主键删除角色权限
     */
    void removeByPermissionId(Long permissionId);

}