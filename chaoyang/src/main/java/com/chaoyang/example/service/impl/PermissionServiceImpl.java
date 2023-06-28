package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.constant.BasicConstant;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.PermissionResponse;
import com.chaoyang.example.entity.po.Permission;
import com.chaoyang.example.entity.po.RolePermission;
import com.chaoyang.example.exception.BusinessException;
import com.chaoyang.example.mapper.PermissionMapper;
import com.chaoyang.example.service.PermissionService;
import com.chaoyang.example.service.RolePermissionService;
import com.chaoyang.example.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
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

    private final LoginUtil loginUtil;

    private final RolePermissionService rolePermissionService;

    @Override
    public boolean notExistsById(Long id) {
        return this.count(new LambdaQueryWrapper<Permission>().eq(Permission::getId, id)) == 0;
    }

    @Override
    public boolean existsByNameOrCode(String name, String code, Long excludeId) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<Permission>()
                .and(nestedQueryWrapper -> {
                    nestedQueryWrapper.eq(Permission::getName, name);
                    nestedQueryWrapper.or();
                    nestedQueryWrapper.eq(Permission::getCode, code);
                }).ne(Objects.nonNull(excludeId), Permission::getId, excludeId);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public List<Permission> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        return this.list(new LambdaQueryWrapper<Permission>().in(Permission::getId, ids));
    }

    @Override
    public Map<Long, Permission> findMapByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }

        List<Permission> permissions = this.findByIds(ids);

        return permissions.stream().collect(Collectors.toMap(Permission::getId, Function.identity()));
    }

    @Override
    public PermissionResponse find(FindPermissionRequest request) {
        Permission permission = this.getById(request.getId());

        if (Objects.isNull(permission)) {
            throw new BusinessException(BusinessException.Message.PERMISSION_NOT_EXISTS);
        }

        return PermissionResponse.of(permission);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Page<PermissionResponse> findPage(FindPermissionPageRequest request) {
        Page<Permission> page = new Page<>(request.getCurrent(), request.getSize());

        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<Permission>()
                .like(Objects.nonNull(request.getName()), Permission::getName, request.getName())
                .like(Objects.nonNull(request.getCode()), Permission::getCode, request.getCode());

        this.page(page, queryWrapper);

        Page<PermissionResponse> responsePage = new Page<>();

        BeanUtils.copyProperties(page, responsePage, "records");

        responsePage.setRecords(page.getRecords().stream().map(PermissionResponse::of).collect(Collectors.toList()));

        return responsePage;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Page<PermissionResponse> findInactivePage(FindInactivePermissionPageRequest request) {
        List<RolePermission> rolePermissions = this.rolePermissionService.findByRoleId(request.getRoleId());

        List<Long> excludedIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

        Page<Permission> page = new Page<>(request.getCurrent(), request.getSize());

        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<Permission>()
                .notIn(!excludedIds.isEmpty(), Permission::getId, excludedIds);

        this.page(page, queryWrapper);

        Page<PermissionResponse> responsePage = new Page<>();

        BeanUtils.copyProperties(page, responsePage, "records");

        responsePage.setRecords(page.getRecords().stream().map(PermissionResponse::of).collect(Collectors.toList()));

        return responsePage;
    }

    @Override
    public void create(CreatePermissionRequest request) {
        if (this.existsByNameOrCode(request.getName(), request.getCode(), null)) {
            throw new BusinessException(BusinessException.Message.PERMISSION_NAME_OR_CODE_EXISTS);
        }

        Permission permission = new Permission();

        permission.setName(request.getName());
        permission.setCode(request.getCode());
        permission.setCreateUserId(this.loginUtil.getLoginInfo().getUserId());
        permission.setCreateDateTime(LocalDateTime.now());

        if (!this.save(permission)) {
            throw new BusinessException(BusinessException.Message.CREATE_PERMISSION_FAIL);
        }
    }

    @Override
    public void modify(ModifyPermissionRequest request) {
        Permission permission = this.getById(request.getId());

        if (Objects.isNull(permission)) {
            throw new BusinessException(BusinessException.Message.PERMISSION_NOT_EXISTS);
        }

        if (Objects.equals(permission.getBasic(), BasicConstant.TRUE)) {
            throw new BusinessException(BusinessException.Message.PERMISSION_CANNOT_MODIFY);
        }

        if (this.existsByNameOrCode(request.getName(), request.getCode(), request.getId())) {
            throw new BusinessException(BusinessException.Message.PERMISSION_NAME_OR_CODE_EXISTS);
        }

        permission.setId(request.getId());
        permission.setName(request.getName());
        permission.setCode(request.getCode());
        permission.setModifyUserId(this.loginUtil.getLoginInfo().getUserId());
        permission.setModifyDateTime(LocalDateTime.now());

        if (!this.updateById(permission)) {
            throw new BusinessException(BusinessException.Message.MODIFY_PERMISSION_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(RemovePermissionRequest request) {
        Permission permission = this.getById(request.getId());

        if (Objects.isNull(permission)) {
            throw new BusinessException(BusinessException.Message.PERMISSION_NOT_EXISTS);
        }

        if (Objects.equals(permission.getBasic(), BasicConstant.TRUE)) {
            throw new BusinessException(BusinessException.Message.PERMISSION_CANNOT_REMOVE);
        }

        if (!this.removeById(request.getId())) {
            throw new BusinessException(BusinessException.Message.REMOVE_PERMISSION_FAIL);
        }

        this.rolePermissionService.removeByPermissionId(request.getId());
    }

}