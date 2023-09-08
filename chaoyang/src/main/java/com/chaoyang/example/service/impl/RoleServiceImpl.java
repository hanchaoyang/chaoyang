package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.constant.BasicConstant;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.RoleResponse;
import com.chaoyang.example.entity.po.Role;
import com.chaoyang.example.entity.po.UserRole;
import com.chaoyang.example.exception.BusinessException;
import com.chaoyang.example.mapper.RoleMapper;
import com.chaoyang.example.service.RolePermissionService;
import com.chaoyang.example.service.RoleService;
import com.chaoyang.example.service.UserRoleService;
import com.chaoyang.example.util.LoginUtil;
import com.chaoyang.example.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 角色服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final LoginUtil loginUtil;

    private final UserRoleService userRoleService;

    private final RolePermissionService rolePermissionService;

    private final RoleMapper roleMapper;

    @Override
    public boolean existsByNameOrCode(String name, String code, Long excludeId) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>()
                .and(nestedQueryWrapper -> {
                    nestedQueryWrapper.eq(Role::getName, name);
                    nestedQueryWrapper.or();
                    nestedQueryWrapper.eq(Role::getCode, code);
                }).ne(Objects.nonNull(excludeId), Role::getId, excludeId);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public boolean notExistsById(Long id) {
        return this.count(new LambdaQueryWrapper<Role>().eq(Role::getId, id)) == 0;
    }

    @Override
    public List<Role> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        return this.list(new LambdaQueryWrapper<Role>().in(Role::getId, ids));
    }

    @Override
    public Map<Long, Role> findMapByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }

        List<Role> roles = this.findByIds(ids);

        return roles.stream().collect(Collectors.toMap(Role::getId, Function.identity()));
    }

    @Override
    public RoleResponse find(FindRoleRequest request) {
        Role role = this.getById(request.getId());

        if (Objects.isNull(role)) {
            throw new BusinessException(BusinessException.Message.ROLE_NOT_EXISTS);
        }

        return RoleResponse.of(role);
    }

    @Override
    public Page<RoleResponse> findPage(FindRolePageRequest request) {
        Page<Role> page = new Page<>(request.getCurrent(), request.getSize());

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>()
                .like(Objects.nonNull(request.getName()), Role::getName, request.getName())
                .like(Objects.nonNull(request.getCode()), Role::getCode, request.getCode());

        this.page(page, queryWrapper);

        Page<RoleResponse> responsePage = new Page<>();

        BeanUtils.copyProperties(page, responsePage, "records");

        responsePage.setRecords(page.getRecords().stream().map(RoleResponse::of).collect(Collectors.toList()));

        return responsePage;
    }

    @Override
    public Page<RoleResponse> findActivePage(FindActiveRolePageRequest request) {
        Page<Role> page = this.roleMapper.selectActivePage(request.getUserId(), new Page<>(request.getCurrent(), request.getSize()));

        return PageUtil.convert(page, RoleResponse::of);
    }

    @Override
    public Page<RoleResponse> findInactivePage(FindInactiveRolePageRequest request) {
        Page<Role> page = this.roleMapper.selectInactivePage(request.getUserId(), new Page<>(request.getCurrent(), request.getSize()));

        return PageUtil.convert(page, RoleResponse::of);
    }

    @Override
    public void create(CreateRoleRequest request) {
        if (this.existsByNameOrCode(request.getName(), request.getCode(), null)) {
            throw new BusinessException(BusinessException.Message.ROLE_NAME_OR_CODE_EXISTS);
        }

        Role role = new Role();

        role.setName(request.getName());
        role.setCode(request.getCode());
        role.setCreateUserId(this.loginUtil.getLoginInfo().getUserId());
        role.setCreateDateTime(LocalDateTime.now());

        if (!this.save(role)) {
            throw new BusinessException(BusinessException.Message.CREATE_ROLE_FAIL);
        }
    }

    @Override
    public void createRolePermission(CreateRolePermissionRequest request) {

    }

    @Override
    public void modify(ModifyRoleRequest request) {
        Role role = this.getById(request.getId());

        if (Objects.isNull(role)) {
            throw new BusinessException(BusinessException.Message.ROLE_NOT_EXISTS);
        }

        if (Objects.equals(role.getBasic(), BasicConstant.TRUE)) {
            throw new BusinessException(BusinessException.Message.ROLE_CANNOT_MODIFY);
        }

        if (this.existsByNameOrCode(request.getName(), request.getCode(), request.getId())) {
            throw new BusinessException(BusinessException.Message.ROLE_NAME_OR_CODE_EXISTS);
        }

        role.setId(request.getId());
        role.setName(request.getName());
        role.setCode(request.getCode());
        role.setModifyUserId(this.loginUtil.getLoginInfo().getUserId());
        role.setModifyDateTime(LocalDateTime.now());

        if (!this.updateById(role)) {
            throw new BusinessException(BusinessException.Message.MODIFY_ROLE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(RemoveRoleRequest request) {
        Role role = this.getById(request.getId());

        if (Objects.isNull(role)) {
            throw new BusinessException(BusinessException.Message.ROLE_NOT_EXISTS);
        }

        if (Objects.equals(role.getBasic(), BasicConstant.TRUE)) {
            throw new BusinessException(BusinessException.Message.ROLE_CANNOT_REMOVE);
        }

        if (!this.removeById(request.getId())) {
            throw new BusinessException(BusinessException.Message.REMOVE_ROLE_FAIL);
        }

        this.userRoleService.removeByRoleId(request.getId());
        this.rolePermissionService.removeByRoleId(request.getId());
    }

}