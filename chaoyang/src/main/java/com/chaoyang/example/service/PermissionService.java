package com.chaoyang.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.PermissionResponse;
import com.chaoyang.example.entity.po.Permission;

import java.util.List;
import java.util.Map;

/**
 * 权限服务层接口
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据名称或标识查询权限是否存在
     */
    boolean existsByNameOrCode(String name, String code, Long excludeId);

    /**
     * 根据ID查询权限是否存在
     */
    boolean notExistsById(Long id);

    /**
     * 根据ID集合查询权限
     */
   List<Permission> findByIds(List<Long> ids);

    /**
     * 根据ID集合查询权限Map集合
     */
    Map<Long, Permission> findMapByIds(List<Long> ids);

    /**
     * 根据ID查询权限
     */
    PermissionResponse find(FindPermissionRequest request);

    /**
     * 根据条件分页查询权限
     */
    Page<PermissionResponse> findPage(FindPermissionPageRequest request);

    /**
     * 根据角色ID分页查询已关联的权限
     */
    Page<PermissionResponse> findActivePage(FindActivePermissionPageRequest request);

    /**
     * 根据角色ID分页查询未关联的权限
     */
    Page<PermissionResponse> findInactivePage(FindInactivePermissionPageRequest request);

    /**
     * 添加权限
     */
    void create(CreatePermissionRequest request);

    /**
     * 修改权限
     */
    void modify(ModifyPermissionRequest request);

    /**
     * 删除权限
     */
    void remove(RemovePermissionRequest request);
}