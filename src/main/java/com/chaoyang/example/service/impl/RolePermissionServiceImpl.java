package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.entity.dto.request.CreateRolePermissionRequest;
import com.chaoyang.example.entity.dto.request.FindRolePermissionPageRequest;
import com.chaoyang.example.entity.dto.request.RemoveRolePermissionRequest;
import com.chaoyang.example.entity.dto.response.RolePermissionResponse;
import com.chaoyang.example.entity.po.Permission;
import com.chaoyang.example.entity.po.RolePermission;
import com.chaoyang.example.exception.BusinessException;
import com.chaoyang.example.mapper.RolePermissionMapper;
import com.chaoyang.example.service.PermissionService;
import com.chaoyang.example.service.RolePermissionService;
import com.chaoyang.example.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 角色权限服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, com.chaoyang.example.entity.po.RolePermission> implements RolePermissionService {

    private final RoleService roleService;

    private final PermissionService permissionService;

    // TODO 循环依赖问题
    @Lazy
    public RolePermissionServiceImpl(RoleService roleService, PermissionService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<com.chaoyang.example.entity.po.RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(com.chaoyang.example.entity.po.RolePermission::getId, id);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public boolean notExistsById(Long id) {
        return !this.existsById(id);
    }

    @Override
    public boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId) {
        LambdaQueryWrapper<com.chaoyang.example.entity.po.RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(com.chaoyang.example.entity.po.RolePermission::getRoleId, roleId);
        queryWrapper.eq(com.chaoyang.example.entity.po.RolePermission::getPermissionId, permissionId);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public List<com.chaoyang.example.entity.po.RolePermission> findByRoleId(Long roleId) {
        LambdaQueryWrapper<com.chaoyang.example.entity.po.RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(com.chaoyang.example.entity.po.RolePermission::getRoleId, roleId);

        return this.list(queryWrapper);
    }

    @Override
    public List<com.chaoyang.example.entity.po.RolePermission> findByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<com.chaoyang.example.entity.po.RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.in(com.chaoyang.example.entity.po.RolePermission::getRoleId, roleIds);

        return this.list(queryWrapper);
    }

    @Override
    public Page<RolePermissionResponse> findRolePermissionPage(FindRolePermissionPageRequest findRolePermissionPageRequest) {
        Page<com.chaoyang.example.entity.po.RolePermission> rolePermissionPage = new Page<>( findRolePermissionPageRequest.getCurrent(), findRolePermissionPageRequest.getSize());

        LambdaQueryWrapper<com.chaoyang.example.entity.po.RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(com.chaoyang.example.entity.po.RolePermission::getRoleId, findRolePermissionPageRequest.getRoleId());

        this.page(rolePermissionPage, queryWrapper);

        Page<RolePermissionResponse> rolePermissionResponsePage = new Page<>();

        BeanUtils.copyProperties(rolePermissionPage, rolePermissionResponsePage, "records");

        List<Long> permissionIds = rolePermissionPage.getRecords().stream().map(com.chaoyang.example.entity.po.RolePermission::getPermissionId).collect(Collectors.toList());

        Map<Long, Permission> permissionMap;

        if (permissionIds.isEmpty()) {
            permissionMap = new HashMap<>();
        } else {
            List<Permission> permissions = this.permissionService.findByIds(permissionIds);

            permissionMap = permissions.stream().collect(Collectors.toMap(Permission::getId, Function.identity()));
        }

        rolePermissionResponsePage.setRecords(rolePermissionPage.getRecords().stream().map(rolePermission -> {
            Long permissionId = rolePermission.getPermissionId();

            Permission permission = permissionMap.get(permissionId);

            RolePermissionResponse findRolePermissionResponsePageResponse = new RolePermissionResponse();

            findRolePermissionResponsePageResponse.setRolePermissionId(rolePermission.getId());
            findRolePermissionResponsePageResponse.setPermissionId(rolePermission.getPermissionId());
            findRolePermissionResponsePageResponse.setPermissionName(permission.getName());
            findRolePermissionResponsePageResponse.setPermissionCode(permission.getCode());

            return findRolePermissionResponsePageResponse;
        }).collect(Collectors.toList()));

        return rolePermissionResponsePage;
    }

    @Override
    public void create(CreateRolePermissionRequest createRolePermissionRequest) {
        if (this.roleService.notExistsById(createRolePermissionRequest.getRoleId())) {
            throw new BusinessException("角色不存在");
        }

        if (this.permissionService.notExistsById(createRolePermissionRequest.getPermissionId())) {
            throw new BusinessException("权限不存在");
        }

        if (this.existsByRoleIdAndPermissionId(createRolePermissionRequest.getRoleId(), createRolePermissionRequest.getPermissionId())) {
            throw new BusinessException("该角色权限已存在");
        }

        com.chaoyang.example.entity.po.RolePermission rolePermission = new com.chaoyang.example.entity.po.RolePermission();

        rolePermission.setRoleId(createRolePermissionRequest.getRoleId());
        rolePermission.setPermissionId(createRolePermissionRequest.getPermissionId());

        if (!this.save(rolePermission)) {
            throw new BusinessException("添加角色权限失败");
        }
    }

    @Override
    public void remove(RemoveRolePermissionRequest removeRolePermissionRequest) {
        /*
         * 判断是否存在
         */
        if (this.notExistsById(removeRolePermissionRequest.getRolePermissionId())) {
            throw new BusinessException("该角色权限不存在");
        }

        /*
         * 删除
         */
        if (!this.removeById(removeRolePermissionRequest.getRolePermissionId())) {
            throw new BusinessException("删除角色权限失败");
        }
    }

    @Override
    public void removeByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(RolePermission::getRoleId, roleId);

        this.remove(queryWrapper);
    }

    @Override
    public void removeByPermissionId(Long permissionId) {
        LambdaQueryWrapper<com.chaoyang.example.entity.po.RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(com.chaoyang.example.entity.po.RolePermission::getPermissionId, permissionId);

        this.remove(queryWrapper);
    }

}