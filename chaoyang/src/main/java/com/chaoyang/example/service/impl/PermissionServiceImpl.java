package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.PermissionResponse;
import com.chaoyang.example.entity.dto.response.RoleResponse;
import com.chaoyang.example.entity.po.Permission;
import com.chaoyang.example.entity.po.Role;
import com.chaoyang.example.entity.po.RolePermission;
import com.chaoyang.example.exception.BusinessException;
import com.chaoyang.example.mapper.PermissionMapper;
import com.chaoyang.example.service.PermissionService;
import com.chaoyang.example.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 权限服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    private final RolePermissionService rolePermissionService;

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Permission::getId, id);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public boolean notExistsById(Long id) {
        return !this.existsById(id);
    }

    @Override
    public boolean existsByNameOrCode(String name, String code, Long excludeId) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.and(nestedQueryWrapper -> {
            nestedQueryWrapper.eq(Permission::getName, name);
            nestedQueryWrapper.or();
            nestedQueryWrapper.eq(Permission::getCode, code);
        });
        queryWrapper.ne(Objects.nonNull(excludeId), Permission::getId, excludeId);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public boolean notExistsByNameOrCode(String name, String code) {
        return !this.existsByNameOrCode(name, code, null);
    }

    @Override
    public List<Permission> findByIds(List<Long> ids) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.in(Permission::getId, ids);

        return this.list(queryWrapper);
    }

    @Override
    public PermissionResponse find(FindPermissionRequest findPermissionRequest) {
        Permission permission = this.getById(findPermissionRequest.getPermissionId());

        if (Objects.isNull(permission)) {
            throw new BusinessException("该权限不存在");
        }

        PermissionResponse permissionResponse = new PermissionResponse();

        permissionResponse.setPermissionId(permission.getId());
        permissionResponse.setPermissionName(permission.getName());
        permissionResponse.setPermissionCode(permission.getCode());

        return permissionResponse;
    }

    @Override
    public Page<PermissionResponse> findPage(FindPermissionPageRequest findPermissionPageRequest) {
        Page<Permission> permissionPage = new Page<>(findPermissionPageRequest.getCurrent(), findPermissionPageRequest.getSize());

        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(Objects.nonNull(findPermissionPageRequest.getPermissionName()), Permission::getName, findPermissionPageRequest.getPermissionName());
        queryWrapper.like(Objects.nonNull(findPermissionPageRequest.getPermissionCode()), Permission::getCode, findPermissionPageRequest.getPermissionCode());

        this.page(permissionPage, queryWrapper);

        Page<PermissionResponse> permissionResponsePage = new Page<>();

        BeanUtils.copyProperties(permissionPage, permissionResponsePage, "records");

        permissionResponsePage.setRecords(permissionPage.getRecords().stream().map(permission -> {
            PermissionResponse permissionResponse = new PermissionResponse();

            permissionResponse.setPermissionId(permission.getId());
            permissionResponse.setPermissionName(permission.getName());
            permissionResponse.setPermissionCode(permission.getCode());

            return permissionResponse;
        }).collect(Collectors.toList()));

        return permissionResponsePage;
    }

    @Override
    public Page<PermissionResponse> findInactivePage(FindInactivePermissionPageRequest findInactivePermissionPageRequest) {
        List<RolePermission> rolePermissions = this.rolePermissionService.findByRoleId(findInactivePermissionPageRequest.getRoleId());

        List<Long> excludedIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

        Page<Permission> permissionPage = new Page<>(findInactivePermissionPageRequest.getCurrent(), findInactivePermissionPageRequest.getSize());

        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.notIn(!excludedIds.isEmpty(), Permission::getId, excludedIds);

        this.page(permissionPage, queryWrapper);

        Page<PermissionResponse> inactivePermissionResponsePage = new Page<>();

        BeanUtils.copyProperties(permissionPage, inactivePermissionResponsePage, "records");

        inactivePermissionResponsePage.setRecords(permissionPage.getRecords().stream().map(permission -> {
            PermissionResponse permissionResponse = new PermissionResponse();

            permissionResponse.setPermissionId(permission.getId());
            permissionResponse.setPermissionName(permission.getName());
            permissionResponse.setPermissionCode(permission.getCode());

            return permissionResponse;
        }).collect(Collectors.toList()));

        return inactivePermissionResponsePage;
    }

    @Override
    public void create(CreatePermissionRequest createPermissionRequest) {
        if (this.existsByNameOrCode(createPermissionRequest.getPermissionName(), createPermissionRequest.getPermissionCode(), null)) {
            throw new BusinessException("该权限名称或标识已存在");
        }

        Permission permission = new Permission();

        permission.setName(createPermissionRequest.getPermissionName());
        permission.setCode(createPermissionRequest.getPermissionCode());

        if (!this.save(permission)) {
            throw new BusinessException("添加权限失败");
        }
    }

    @Override
    public void modify(ModifyPermissionRequest modifyPermissionRequest) {
        if (this.notExistsById(modifyPermissionRequest.getPermissionId())) {
            throw new BusinessException("该权限不存在");
        }

        if (this.existsByNameOrCode(modifyPermissionRequest.getPermissionName(), modifyPermissionRequest.getPermissionCode(), modifyPermissionRequest.getPermissionId())) {
            throw new BusinessException("该权限名称或标识已存在");
        }

        Permission permission = new Permission();

        permission.setId(modifyPermissionRequest.getPermissionId());
        permission.setName(modifyPermissionRequest.getPermissionName());
        permission.setCode(modifyPermissionRequest.getPermissionCode());

        if (!this.updateById(permission)) {
            throw new BusinessException("修改权限失败");
        }
    }

    @Override
    public void remove(RemovePermissionRequest removePermissionRequest) {
        if (this.notExistsById(removePermissionRequest.getPermissionId())) {
            throw new BusinessException("该权限不存在");
        }

        if (!this.removeById(removePermissionRequest.getPermissionId())) {
            throw new BusinessException("删除权限失败");
        }

        this.rolePermissionService.removeByPermissionId(removePermissionRequest.getPermissionId());
    }

}