package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.PermissionResponse;
import com.chaoyang.example.entity.dto.response.InactivePermissionResponse;
import com.chaoyang.example.entity.po.Permission;
import com.chaoyang.example.entity.po.RolePermission;
import com.chaoyang.example.exception.BusinessException;
import com.chaoyang.example.mapper.PermissionMapper;
import com.chaoyang.example.service.PermissionService;
import com.chaoyang.example.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean existsByNameOrCode(String name, String code) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Permission::getName, name);
        queryWrapper.or();
        queryWrapper.eq(Permission::getCode, code);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public boolean notExistsByNameOrCode(String name, String code) {
        return !this.existsByNameOrCode(name, code);
    }

    @Override
    public Permission findById(Long id) {
        return this.getById(id);
    }

    @Override
    public List<Permission> findByIds(List<Long> ids) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.in(Permission::getId, ids);

        return this.list(queryWrapper);
    }

    @Override
    public List<Permission> findAll() {
        return this.list();
    }

    @Override
    public PermissionResponse findPermissionResponse(FindPermissionRequest findPermissionRequest) {
        Permission permission = this.getById(findPermissionRequest.getPermissionId());

        if (Objects.isNull(permission)) {
            throw new BusinessException("权限不存在");
        }

        PermissionResponse permissionResponse = new PermissionResponse();

        permissionResponse.setPermissionId(permission.getId());
        permissionResponse.setPermissionName(permission.getName());
        permissionResponse.setPermissionCode(permission.getCode());

        return permissionResponse;
    }

    @Override
    public Page<InactivePermissionResponse> findInactivePermissionPage(FindInactivePermissionPageRequest findInactivePermissionPageRequest) {
        List<RolePermission> rolePermissions = this.rolePermissionService.findByRoleId(findInactivePermissionPageRequest.getRoleId());

        List<Long> excludedIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

        Page<Permission> permissionPage = new Page<>(findInactivePermissionPageRequest.getCurrent(), findInactivePermissionPageRequest.getSize());

        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.notIn(!excludedIds.isEmpty(), Permission::getId, excludedIds);

        this.page(permissionPage, queryWrapper);

        Page<InactivePermissionResponse> inactivePermissionResponsePage = new Page<>();

        BeanUtils.copyProperties(permissionPage, inactivePermissionResponsePage, "records");

        inactivePermissionResponsePage.setRecords(permissionPage.getRecords().stream().map(permission -> {
            InactivePermissionResponse inactivePermissionResponse = new InactivePermissionResponse();

            inactivePermissionResponse.setPermissionId(permission.getId());
            inactivePermissionResponse.setPermissionName(permission.getName());
            inactivePermissionResponse.setPermissionCode(permission.getCode());

            return inactivePermissionResponse;
        }).collect(Collectors.toList()));

        return inactivePermissionResponsePage;
    }

    @Override
    public void create(CreatePermissionRequest createPermissionRequest) {
        /*
         * 判断权限名称或标识是否已存在
         */
        if (this.notExistsByNameOrCode(createPermissionRequest.getPermissionName(), createPermissionRequest.getPermissionCode())) {
            throw new BusinessException("该权限名称或标识已存在");
        }

        /*
         * 保存
         */
        Permission permission = new Permission();

        permission.setName(createPermissionRequest.getPermissionName());
        permission.setCode(createPermissionRequest.getPermissionCode());

        if (!this.save(permission)) {
            throw new BusinessException("添加权限失败");
        }
    }

    @Override
    public void modify(ModifyPermissionRequest modifyPermissionRequest) {
        /*
         * 判断是否存在
         */
        if (this.notExistsById(modifyPermissionRequest.getPermissionId())) {
            throw new BusinessException("该权限不存在");
        }

        /*
         * 判断权限名称或标识是否已存在
         */
        if (this.notExistsByNameOrCode(modifyPermissionRequest.getPermissionName(), modifyPermissionRequest.getPermissionCode())) {
            throw new BusinessException("该权限名称或标识已存在");
        }

        /*
         * 保存
         */
        Permission permission = new Permission();

        permission.setId(modifyPermissionRequest.getPermissionId());
        permission.setName(modifyPermissionRequest.getPermissionName());
        permission.setCode(modifyPermissionRequest.getPermissionCode());

        if (!this.updateById(permission)) {
            throw new BusinessException("修改权限失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(RemovePermissionRequest removePermissionRequest) {
        /*
         * 判断是否存在
         */
        if (this.notExistsById(removePermissionRequest.getPermissionId())) {
            throw new BusinessException("该权限不存在");
        }

        /*
         * 删除
         */
        if (!this.removeById(removePermissionRequest.getPermissionId())) {
            throw new BusinessException("删除权限失败");
        }

        /*
         * 删除角色权限关联
         */
        this.rolePermissionService.removeByPermissionId(removePermissionRequest.getPermissionId());
    }

}