package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.FindRolePageResponse;
import com.chaoyang.example.entity.dto.response.FindRoleResponse;
import com.chaoyang.example.entity.po.Role;
import com.chaoyang.example.exception.BusinessException;
import com.chaoyang.example.mapper.RoleMapper;
import com.chaoyang.example.service.RolePermissionService;
import com.chaoyang.example.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    private final RolePermissionService rolePermissionService;

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Role::getId, id);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public boolean notExistsById(Long id) {
        return !this.existsById(id);
    }

    @Override
    public boolean existsByNameOrCode(String name, String code, Long excludeId) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.and(nestedQueryWrapper -> {
            nestedQueryWrapper.eq(Role::getName, name);
            nestedQueryWrapper.or();
            nestedQueryWrapper.eq(Role::getCode, code);
        });
        queryWrapper.ne(Role::getId, excludeId);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public boolean notExistsByNameOrCode(String name, String code) {
        return !this.existsByNameOrCode(name, code, null);
    }

    @Override
    public Role findById(Long id) {
        return this.getById(id);
    }

    @Override
    public List<Role> findByIds(List<Long> ids) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.in(Role::getId, ids);

        return this.list(queryWrapper);
    }

    @Override
    public List<Role> findAll() {
        return this.list();
    }

    @Override
    public FindRoleResponse findRoleResponse(FindRoleRequest findRoleRequest) {
        Role role = this.getById(findRoleRequest.getRoleId());

        if (Objects.isNull(role)) {
            throw new BusinessException("该角色不存在");
        }

        FindRoleResponse findRoleResponse = new FindRoleResponse();

        findRoleResponse.setRoleId(role.getId());
        findRoleResponse.setRoleName(role.getName());
        findRoleResponse.setRoleCode(role.getCode());

        return findRoleResponse;
    }

    @Override
    public Page<FindRolePageResponse> findRolePage(FindRolePageRequest findRolePageRequest) {
        Page<Role> rolePage = new Page<>(findRolePageRequest.getCurrent(), findRolePageRequest.getSize());

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(Objects.nonNull(findRolePageRequest.getRoleName()), Role::getName, findRolePageRequest.getRoleName());
        queryWrapper.like(Objects.nonNull(findRolePageRequest.getRoleCode()), Role::getCode, findRolePageRequest.getRoleCode());

        this.page(rolePage, queryWrapper);

        Page<FindRolePageResponse> roleResponsePage = new Page<>();

        BeanUtils.copyProperties(rolePage, roleResponsePage, "records");

        roleResponsePage.setRecords(rolePage.getRecords().stream().map(role -> {
            FindRolePageResponse findRolePageResponse = new FindRolePageResponse();

            findRolePageResponse.setRoleId(role.getId());
            findRolePageResponse.setRoleName(role.getName());
            findRolePageResponse.setRoleCode(role.getCode());

            return findRolePageResponse;
        }).collect(Collectors.toList()));

        return roleResponsePage;
    }

    @Override
    public void create(CreateRoleRequest createRoleRequest) {
        /*
         * 判断角色名称或标识是否已存在
         */
        if (this.existsByNameOrCode(createRoleRequest.getRoleName(), createRoleRequest.getRoleCode(), null)) {
            throw new BusinessException("该角色名称或标识已存在");
        }

        /*
         * 保存
         */
        Role role = new Role();

        role.setName(createRoleRequest.getRoleName());
        role.setCode(createRoleRequest.getRoleCode());

        if (!this.save(role)) {
            throw new BusinessException("添加角色失败");
        }
    }

    @Override
    public void modify(ModifyRoleRequest modifyRoleRequest) {
        /*
         * 判断是否存在
         */
        if (this.notExistsById(modifyRoleRequest.getRoleId())) {
            throw new BusinessException("该角色不存在");
        }

        /*
         * 判断权限名称或标识是否已存在
         */
        if (this.existsByNameOrCode(modifyRoleRequest.getRoleName(), modifyRoleRequest.getRoleCode(), modifyRoleRequest.getRoleId())) {
            throw new BusinessException("该角色名称或标识已存在");
        }

        /*
         * 保存
         */
        Role role = new Role();

        role.setId(modifyRoleRequest.getRoleId());
        role.setName(modifyRoleRequest.getRoleName());
        role.setCode(modifyRoleRequest.getRoleCode());

        if (!this.updateById(role)) {
            throw new BusinessException("修改角色失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(RemoveRoleRequest removeRoleRequest) {
        /*
         * 判断是否存在
         */
        if (this.notExistsById(removeRoleRequest.getRoleId())) {
            throw new BusinessException("该角色不存在");
        }

        /*
         * 删除
         */
        if (!this.removeById(removeRoleRequest.getRoleId())) {
            throw new BusinessException("删除角色失败");
        }

        /*
         * 删除角色权限关联
         */
        this.rolePermissionService.removeByRoleId(removeRoleRequest.getRoleId());
    }

}