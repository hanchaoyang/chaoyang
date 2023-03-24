package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.entity.dto.request.CreateRolePermissionRequest;
import com.chaoyang.example.entity.dto.request.FindRolePermissionPageRequest;
import com.chaoyang.example.entity.dto.request.RemoveRolePermissionRequest;
import com.chaoyang.example.entity.dto.response.FindRolePermissionPageResponse;
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
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

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
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(RolePermission::getId, id);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public boolean notExistsById(Long id) {
        return !this.existsById(id);
    }

    @Override
    public boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(RolePermission::getRoleId, roleId);
        queryWrapper.eq(RolePermission::getPermissionId, permissionId);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public List<RolePermission> findByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(RolePermission::getRoleId, roleId);

        return this.list(queryWrapper);
    }

    @Override
    public List<RolePermission> findByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.in(RolePermission::getRoleId, roleIds);

        return this.list(queryWrapper);
    }

    @Override
    public Page<FindRolePermissionPageResponse> findRolePermissionPage(FindRolePermissionPageRequest findRolePermissionPageRequest) {
        Page<RolePermission> rolePermissionPage = new Page<>( findRolePermissionPageRequest.getCurrent(), findRolePermissionPageRequest.getSize());

        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(RolePermission::getRoleId, findRolePermissionPageRequest.getRoleId());

        this.page(rolePermissionPage, queryWrapper);

        Page<FindRolePermissionPageResponse> rolePermissionResponsePage = new Page<>();

        BeanUtils.copyProperties(rolePermissionPage, rolePermissionResponsePage, "records");

        List<Long> permissionIds = rolePermissionPage.getRecords().stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

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

            FindRolePermissionPageResponse findRolePermissionPageResponse = new FindRolePermissionPageResponse();

            findRolePermissionPageResponse.setRolePermissionId(rolePermission.getId());
            findRolePermissionPageResponse.setPermissionId(rolePermission.getPermissionId());
            findRolePermissionPageResponse.setPermissionName(permission.getName());
            findRolePermissionPageResponse.setPermissionCode(permission.getCode());

            return findRolePermissionPageResponse;
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

        RolePermission rolePermission = new RolePermission();

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
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(RolePermission::getPermissionId, permissionId);

        this.remove(queryWrapper);
    }

}