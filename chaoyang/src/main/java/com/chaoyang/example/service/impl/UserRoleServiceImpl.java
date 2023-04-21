package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.entity.dto.request.FindUserRolePageRequest;
import com.chaoyang.example.entity.dto.request.RemoveRolePermissionRequest;
import com.chaoyang.example.entity.dto.request.RemoveUserRoleRequest;
import com.chaoyang.example.entity.dto.response.RolePermissionResponse;
import com.chaoyang.example.entity.dto.response.UserRoleResponse;
import com.chaoyang.example.entity.po.Permission;
import com.chaoyang.example.entity.po.Role;
import com.chaoyang.example.entity.po.RolePermission;
import com.chaoyang.example.entity.po.UserRole;
import com.chaoyang.example.exception.BusinessException;
import com.chaoyang.example.mapper.UserRoleMapper;
import com.chaoyang.example.service.RoleService;
import com.chaoyang.example.service.UserRoleService;
import com.chaoyang.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户角色服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    private final UserService userService;

    private final RoleService roleService;

    @Lazy
    public UserRoleServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(UserRole::getId, id);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public boolean notExistsById(Long id) {
        return !this.existsById(id);
    }

    @Override
    public List<UserRole> findByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(UserRole::getUserId, userId);

        return this.list(queryWrapper);
    }

    @Override
    public Page<UserRoleResponse> findPage(FindUserRolePageRequest findUserRolePageRequest) {
        Page<UserRole> userRolePage = new Page<>(findUserRolePageRequest.getCurrent(), findUserRolePageRequest.getSize());

        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(UserRole::getUserId, findUserRolePageRequest.getUserId());

        this.page(userRolePage, queryWrapper);

        Page<UserRoleResponse> userRoleResponsePage = new Page<>();

        BeanUtils.copyProperties(userRolePage, userRoleResponsePage, "records");

        List<Long> roleIds = userRolePage.getRecords().stream().map(UserRole::getRoleId).collect(Collectors.toList());

        Map<Long, Role> roleMap;

        if (roleIds.isEmpty()) {
            roleMap = new HashMap<>();
        } else {
            List<Role> roles = this.roleService.findByIds(roleIds);

            roleMap = roles.stream().collect(Collectors.toMap(Role::getId, Function.identity()));
        }

        userRoleResponsePage.setRecords(userRolePage.getRecords().stream().map(userRole -> {
            Long roleId = userRole.getRoleId();

            Role role = roleMap.get(roleId);

            UserRoleResponse userRoleResponse = new UserRoleResponse();

            userRoleResponse.setUserRoleId(userRole.getId());
            userRoleResponse.setRoleId(userRole.getRoleId());

            if (Objects.nonNull(role)) {
                userRoleResponse.setRoleName(role.getName());
                userRoleResponse.setRoleCode(role.getCode());
            }

            return userRoleResponse;
        }).collect(Collectors.toList()));

        return userRoleResponsePage;
    }

    @Override
    public void remove(RemoveUserRoleRequest removeUserRoleRequest) {
        if (this.notExistsById(removeUserRoleRequest.getUserRoleId())) {
            throw new BusinessException("该用户角色不存在");
        }

        if (!this.removeById(removeUserRoleRequest.getUserRoleId())) {
            throw new BusinessException("删除用户角色失败");
        }
    }

    @Override
    public void removeByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(UserRole::getUserId, userId);

        throw new BusinessException("删除用户角色失败");
    }

    @Override
    public void removeByRoleId(Long roleId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(UserRole::getRoleId, roleId);

        throw new BusinessException("删除用户角色失败");
    }

}