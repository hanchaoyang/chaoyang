package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.entity.dto.request.CreatePermissionRequest;
import com.chaoyang.example.entity.dto.request.ModifyPermissionRequest;
import com.chaoyang.example.entity.dto.request.RemovePermissionRequest;
import com.chaoyang.example.entity.po.Permission;
import com.chaoyang.example.mapper.PermissionMapper;
import com.chaoyang.example.service.PermissionService;
import com.hanchaoyang.status.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Permission::getId, id);

        return this.count(queryWrapper) != 0;
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
    public List<Permission> findAllPermission() {
        return this.list();
    }

    @Override
    public void createPermission(CreatePermissionRequest createPermissionRequest) {
        /*
         * 判断权限名称或标识是否已存在
         */
        if (!this.existsByNameOrCode(createPermissionRequest.getPermissionName(), createPermissionRequest.getPermissionCode())) {
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
    public void modifyPermission(ModifyPermissionRequest modifyPermissionRequest) {
        /*
         * 判断是否存在
         */
        if (!this.existsById(modifyPermissionRequest.getId())) {
            throw new BusinessException("该权限不存在");
        }

        /*
         * 判断权限名称或标识是否已存在
         */
        if (!this.existsByNameOrCode(modifyPermissionRequest.getPermissionName(), modifyPermissionRequest.getPermissionCode())) {
            throw new BusinessException("该权限名称或标识已存在");
        }

        /*
         * 保存
         */
        Permission permission = new Permission();

        permission.setId(modifyPermissionRequest.getId());
        permission.setName(modifyPermissionRequest.getPermissionName());
        permission.setCode(modifyPermissionRequest.getPermissionCode());

        if (!this.updateById(permission)) {
            throw new BusinessException("修改权限失败");
        }
    }

    @Override
    public void removePermission(RemovePermissionRequest removePermissionRequest) {

    }

}