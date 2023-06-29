package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.constant.BasicConstant;
import com.chaoyang.example.entity.dto.request.CreateUserRoleRequest;
import com.chaoyang.example.entity.dto.request.FindUserRolePageRequest;
import com.chaoyang.example.entity.dto.request.RemoveUserRoleRequest;
import com.chaoyang.example.entity.dto.response.UserRoleResponse;
import com.chaoyang.example.entity.po.Role;
import com.chaoyang.example.entity.po.UserRole;
import com.chaoyang.example.exception.BusinessException;
import com.chaoyang.example.mapper.UserRoleMapper;
import com.chaoyang.example.service.RoleService;
import com.chaoyang.example.service.UserRoleService;
import com.chaoyang.example.service.UserService;
import com.chaoyang.example.util.LoginUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户角色关联服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    private final LoginUtil loginUtil;

    private final UserService userService;

    private final RoleService roleService;

    @Lazy
    public UserRoleServiceImpl(LoginUtil loginUtil, UserService userService, RoleService roleService) {
        this.loginUtil = loginUtil;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public boolean existsByUserIdAndRoleId(Long userId, Long roleId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, roleId);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public List<UserRole> findByUserId(Long userId) {
        return this.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
    }

    @Override
    public UserRole findByUserIdAndRoleId(Long userId, Long roleId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, roleId);

        return this.getOne(queryWrapper);
    }

    @Override
    public Page<UserRoleResponse> findPage(FindUserRolePageRequest request) {
        Page<UserRole> page = new Page<>(request.getCurrent(), request.getSize());

        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, request.getUserId());

        this.page(page, queryWrapper);

        Page<UserRoleResponse> responsePage = new Page<>();

        BeanUtils.copyProperties(page, responsePage, "records");

        List<Long> roleIds = page.getRecords().stream().map(UserRole::getRoleId).collect(Collectors.toList());

        Map<Long, Role> roleMap = this.roleService.findMapByIds(roleIds);

        responsePage.setRecords(page.getRecords().stream().map(userRole -> UserRoleResponse.of(userRole, null, roleMap)).collect(Collectors.toList()));

        return responsePage;
    }

    @Override
    public void create(CreateUserRoleRequest request) {
        if (this.userService.notExistsById(request.getUserId())) {
            throw new BusinessException(BusinessException.Message.USER_NOT_EXISTS);
        }

        if (this.roleService.notExistsById(request.getRoleId())) {
            throw new BusinessException(BusinessException.Message.ROLE_NOT_EXISTS);
        }

        if (this.existsByUserIdAndRoleId(request.getUserId(), request.getRoleId())) {
            throw new BusinessException(BusinessException.Message.USER_ROLE_EXISTS);
        }

        UserRole userRole = new UserRole();

        userRole.setUserId(request.getUserId());
        userRole.setRoleId(request.getRoleId());
        userRole.setCreateUserId(this.loginUtil.getLoginInfo().getUserId());
        userRole.setCreateDateTime(LocalDateTime.now());

        if (!this.save(userRole)) {
            throw new BusinessException(BusinessException.Message.CREATE_USER_ROLE_FAIL);
        }
    }

    @Override
    public void remove(RemoveUserRoleRequest request) {
        UserRole userRole = this.findByUserIdAndRoleId(request.getUserId(), request.getRoleId());

        if (Objects.isNull(userRole)) {
            throw new BusinessException(BusinessException.Message.USER_ROLE_NOT_EXISTS);
        }

        if (Objects.equals(userRole.getBasic(), BasicConstant.TRUE)) {
            throw new BusinessException(BusinessException.Message.USER_ROLE_CANNOT_REMOVE);
        }

        if (!this.removeById(userRole.getId())) {
            throw new BusinessException(BusinessException.Message.REMOVE_USER_ROLE_FAIL);
        }
    }

    @Override
    public void removeByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getBasic, BasicConstant.FALSE);

        this.remove(queryWrapper);
    }

    @Override
    public void removeByRoleId(Long roleId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getRoleId, roleId)
                .eq(UserRole::getBasic, BasicConstant.FALSE);

        this.remove(queryWrapper);
    }

}