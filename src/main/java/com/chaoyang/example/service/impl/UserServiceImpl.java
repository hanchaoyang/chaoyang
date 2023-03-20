package com.chaoyang.example.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.constant.UserStatusConstant;
import com.chaoyang.example.entity.dto.request.CreateUserRequest;
import com.chaoyang.example.entity.dto.request.ModifyUserRequest;
import com.chaoyang.example.entity.dto.request.ModifyUserStatusRequest;
import com.chaoyang.example.entity.dto.request.RemoveUserRequest;
import com.chaoyang.example.entity.po.User;
import com.chaoyang.example.mapper.UserMapper;
import com.chaoyang.example.service.UserRoleService;
import com.chaoyang.example.service.UserService;
import com.hanchaoyang.status.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 用户服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserRoleService userRoleService;

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getId, id);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public boolean notExistsById(Long id) {
        return !this.existsById(id);
    }

    @Override
    public boolean existsByPhone(String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getPhone, phone);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public User findByPhoneAndPassword(String phone, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getPhone, phone);
        queryWrapper.eq(User::getPassword, password);

        return this.getOne(queryWrapper);
    }

    @Override
    public void create(CreateUserRequest createUserRequest) {
        if (this.existsByPhone(createUserRequest.getUserPhone())) {
            throw new BusinessException("该用户已存在");
        }

        User user = new User();

        user.setNickname(createUserRequest.getUserNickname());
        user.setPhone(createUserRequest.getUserPhone());
        user.setPassword(DigestUtil.md5Hex(DigestUtil.md5Hex(createUserRequest.getUserPassword())));
        user.setStatus(UserStatusConstant.ENABLE);

        if (!this.save(user)) {
            throw new BusinessException("添加用户失败");
        }
    }

    @Override
    public void modify(ModifyUserRequest modifyUserRequest) {
        if (this.notExistsById(modifyUserRequest.getUserId())) {
            throw new BusinessException("该用户不存在");
        }

        User user = new User();

        user.setId(modifyUserRequest.getUserId());
        user.setNickname(modifyUserRequest.getUserNickname());
        user.setPhone(modifyUserRequest.getUserPhone());
        user.setPassword(modifyUserRequest.getUserPassword());

        if (!this.updateById(user)) {
            throw new BusinessException("修改用户失败");
        }
    }

    @Override
    public void modifyStatus(ModifyUserStatusRequest modifyUserStatusRequest) {
        if (!Objects.equals(modifyUserStatusRequest.getUserStatus(), UserStatusConstant.DISABLE) && !Objects.equals(modifyUserStatusRequest.getUserStatus(), UserStatusConstant.ENABLE)) {
            throw new BusinessException("用户状态参数错误");
        }

        if (this.notExistsById(modifyUserStatusRequest.getUserId())) {
            throw new BusinessException("该用户不存在");
        }

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();

        updateWrapper.set(User::getId, modifyUserStatusRequest.getUserId());
        updateWrapper.set(User::getStatus, modifyUserStatusRequest.getUserStatus());

        if (!this.update(updateWrapper)) {
            throw new BusinessException("修改用户状态失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(RemoveUserRequest removeUserRequest) {
        if (this.notExistsById(removeUserRequest.getUserId())) {
            throw new BusinessException("该用户不存在");
        }

        if (!this.removeById(removeUserRequest.getUserId())) {
            throw new BusinessException("删除用户失败");
        }

        this.userRoleService.removeByUserId(removeUserRequest.getUserId());
    }

}