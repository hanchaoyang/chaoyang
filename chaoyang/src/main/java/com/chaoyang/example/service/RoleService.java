package com.chaoyang.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.RoleResponse;
import com.chaoyang.example.entity.po.Role;

import java.util.List;
import java.util.Map;

/**
 * 角色服务层接口
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据名称或标识查询角色是否存在
     */
    boolean existsByNameOrCode(String name, String code, Long excludeId);

    /**
     * 根据ID查询角色是否不存在
     */
    boolean notExistsById(Long id);

    /**
     * 根据ID集合查询角色
     */
    List<Role> findByIds(List<Long> ids);

    /**
     * 根据ID集合查询角色Map集合
     */
    Map<Long, Role> findMapByIds(List<Long> ids);

    /**
     * 根据ID查询角色
     */
    RoleResponse find(FindRoleRequest request);

    /**
     * 根据条件分页查询角色
     */
    Page<RoleResponse> findPage(FindRolePageRequest request);

    /**
     * 根据用户ID分页查询未关联的角色
     */
    Page<RoleResponse> findInactivePage(FindInactiveRolePageRequest request);

    /**
     * 添加角色
     */
    void create(CreateRoleRequest request);

    /**
     * 修改角色
     */
    void modify(ModifyRoleRequest request);

    /**
     * 删除角色
     */
    void remove(RemoveRoleRequest request);

}