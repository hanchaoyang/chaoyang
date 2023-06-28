package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.constant.BasicConstant;
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
import com.chaoyang.example.util.LoginUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色权限关联服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    private final LoginUtil loginUtil;

    private final RoleService roleService;

    private final PermissionService permissionService;

    @Lazy
    public RolePermissionServiceImpl(LoginUtil loginUtil, RoleService roleService, PermissionService permissionService) {
        this.loginUtil = loginUtil;
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @Override
    public boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getRoleId, roleId)
                .eq(RolePermission::getPermissionId, permissionId);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public List<RolePermission> findByRoleId(Long roleId) {
        return this.list(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId));
    }

    @Override
    public List<RolePermission> findByRoleIds(List<Long> roleIds) {
        if (roleIds.isEmpty()) {
            return new ArrayList<>();
        }

        return this.list(new LambdaQueryWrapper<RolePermission>().in(RolePermission::getRoleId, roleIds));
    }

    @Override
    public Page<RolePermissionResponse> findPage(FindRolePermissionPageRequest request) {
        Page<RolePermission> page = new Page<>(request.getCurrent(), request.getSize());

        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, request.getRoleId());

        this.page(page, queryWrapper);

        Page<RolePermissionResponse> responsePage = new Page<>();

        BeanUtils.copyProperties(page, responsePage, "records");

        List<Long> permissionIds = page.getRecords().stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

        Map<Long, Permission> permissionMap = this.permissionService.findMapByIds(permissionIds);

        responsePage.setRecords(page.getRecords().stream().map(rolePermission -> RolePermissionResponse.of(rolePermission, null, permissionMap)).collect(Collectors.toList()));

        return responsePage;
    }

    @Override
    public void create(CreateRolePermissionRequest request) {
        if (this.roleService.notExistsById(request.getRoleId())) {
            throw new BusinessException(BusinessException.Message.ROLE_NOT_EXISTS);
        }

        if (this.permissionService.notExistsById(request.getPermissionId())) {
            throw new BusinessException(BusinessException.Message.PERMISSION_NOT_EXISTS);
        }

        if (this.existsByRoleIdAndPermissionId(request.getRoleId(), request.getPermissionId())) {
            throw new BusinessException(BusinessException.Message.ROLE_PERMISSION_EXISTS);
        }

        RolePermission rolePermission = new RolePermission();

        rolePermission.setRoleId(request.getRoleId());
        rolePermission.setPermissionId(request.getPermissionId());
        rolePermission.setCreateUserId(this.loginUtil.getLoginInfo().getUserId());
        rolePermission.setCreateDateTime(LocalDateTime.now());

        if (!this.save(rolePermission)) {
            throw new BusinessException(BusinessException.Message.CREATE_ROLE_PERMISSION_FAIL);
        }
    }

    @Override
    public void remove(RemoveRolePermissionRequest request) {
        RolePermission rolePermission = this.getById(request.getId());

        if (Objects.isNull(rolePermission)) {
            throw new BusinessException(BusinessException.Message.ROLE_PERMISSION_NOT_EXISTS);
        }

        if (Objects.equals(rolePermission.getBasic(), BasicConstant.TRUE)) {
            throw new BusinessException(BusinessException.Message.ROLE_PERMISSION_CANNOT_REMOVE);
        }

        if (!this.removeById(request.getId())) {
            throw new BusinessException(BusinessException.Message.REMOVE_ROLE_PERMISSION_FAIL);
        }
    }

    @Override
    public void removeByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getRoleId, roleId)
                .eq(RolePermission::getBasic, BasicConstant.FALSE);

        this.remove(queryWrapper);
    }

    @Override
    public void removeByPermissionId(Long permissionId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getPermissionId, permissionId)
                .eq(RolePermission::getBasic, BasicConstant.FALSE);

        this.remove(queryWrapper);
    }

}