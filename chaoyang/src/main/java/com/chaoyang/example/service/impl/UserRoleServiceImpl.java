package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.entity.po.UserRole;
import com.chaoyang.example.exception.BusinessException;
import com.chaoyang.example.mapper.UserRoleMapper;
import com.chaoyang.example.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 用户角色服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public List<UserRole> findByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(UserRole::getUserId, userId);

        return this.list(queryWrapper);
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
