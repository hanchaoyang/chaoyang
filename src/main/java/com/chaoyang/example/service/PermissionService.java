package com.chaoyang.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.PermissionResponse;
import com.chaoyang.example.entity.po.Permission;

import java.util.List;

/**
 * 权限服务层接口
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据主键查询权限是否存在
     */
    boolean existsById(Long id);

    /**
     * 根据主键查询权限是否存在
     */
    boolean notExistsById(Long id);

    /**
     * 根据名称或标识查询权限是否存在
     */
    boolean existsByNameOrCode(String name, String code);

    /**
     * 根据名称或标识查询权限是否不存在
     */
    boolean notExistsByNameOrCode(String name, String code);

    /**
     * 根据主键集合查询权限
     */
   List<Permission> findByIds(List<Long> ids);

    /**
     * 根据主键集合查询
     */
    PermissionResponse find(FindPermissionRequest findPermissionRequest);

    /**
     * 根据角色主键分页查询未关联的权限
     */
    Page<PermissionResponse> findInactivePage(FindInactivePermissionPageRequest findInactivePermissionPageRequest);

    /**
     * 添加权限
     */
    void create(CreatePermissionRequest createPermissionRequest);

    /**
     * 修改权限
     */
    void modify(ModifyPermissionRequest modifyPermissionRequest);

    /**
     * 删除权限
     */
    void remove(RemovePermissionRequest removePermissionRequest);

}