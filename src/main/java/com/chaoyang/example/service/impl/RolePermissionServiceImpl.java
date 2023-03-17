package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.entity.po.RolePermission;
import com.chaoyang.example.mapper.RolePermissionMapper;
import com.chaoyang.example.service.RolePermissionService;
import com.hanchaoyang.status.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 角色权限服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Override
    public void removeByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(RolePermission::getRoleId, roleId);

        if (!this.remove(queryWrapper)) {
            throw new BusinessException("删除角色权限失败");
        }
    }

    @Override
    public void removeByPermissionId(Long permissionId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(RolePermission::getPermissionId, permissionId);

        if (!this.remove(queryWrapper)) {
            throw new BusinessException("删除角色权限失败");
        }
    }

}