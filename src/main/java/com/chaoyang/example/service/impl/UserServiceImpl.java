package com.chaoyang.example.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.constant.UserStatusConstant;
import com.chaoyang.example.entity.dto.request.CreateUserRequest;
import com.chaoyang.example.entity.dto.request.ModifyUserRequest;
import com.chaoyang.example.entity.po.User;
import com.chaoyang.example.mapper.UserMapper;
import com.chaoyang.example.service.UserService;
import com.hanchaoyang.status.BusinessException;
import org.springframework.stereotype.Service;

/**
 * 用户服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

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
    public void create(CreateUserRequest createUserRequest) {
        if (this.existsByPhone(createUserRequest.getPhone())) {
            throw new BusinessException("该用户已存在");
        }

        User user = new User();

        user.setNickname(createUserRequest.getNickname());
        user.setPhone(createUserRequest.getPhone());
        user.setPassword(DigestUtil.md5Hex(DigestUtil.md5Hex(createUserRequest.getPassword())));
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

}