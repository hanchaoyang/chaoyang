package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.constant.BasicConstant;
import com.chaoyang.example.constant.UserStatusConstant;
import com.chaoyang.example.entity.dto.LoginInfo;
import com.chaoyang.example.entity.dto.request.*;
import com.chaoyang.example.entity.dto.response.UserResponse;
import com.chaoyang.example.entity.po.User;
import com.chaoyang.example.exception.BusinessException;
import com.chaoyang.example.exception.ParameterException;
import com.chaoyang.example.mapper.UserMapper;
import com.chaoyang.example.service.UserRoleService;
import com.chaoyang.example.service.UserService;
import com.chaoyang.example.util.LoginUtil;
import com.chaoyang.example.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final LoginUtil loginUtil;

    private final UserRoleService userRoleService;

    @Override
    public boolean existsByAccount(String account, Long id) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getAccount, account)
                .ne(Objects.nonNull(id), User::getId, id);

        return this.count(queryWrapper) != 0;
    }

    @Override
    public boolean notExistsById(Long id) {
        return this.count(new LambdaQueryWrapper<User>().eq(User::getId, id)) == 0;
    }

    @Override
    public User findByAccountAndPassword(String account, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .select(User::getId, User::getNickname, User::getAccount, User::getStatus, User::getBasic)
                .eq(User::getAccount, account)
                .eq(User::getPassword, PasswordUtil.encrypt(password));

        return this.getOne(queryWrapper);
    }

    @Override
    public UserResponse find(FindUserRequest request) {
        User user = this.getById(request.getId());

        if (Objects.isNull(user)) {
            throw new BusinessException(BusinessException.Message.USER_NOT_EXISTS);
        }

        return UserResponse.of(user);
    }

    @Override
    public Page<UserResponse> findPage(FindUserPageRequest request) {
        /*
         * 分页查询
         */
        Page<User> page = new Page<>(request.getCurrent(), request.getSize());

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .like(Objects.nonNull(request.getNickname()), User::getNickname, request.getNickname())
                .like(Objects.nonNull(request.getAccount()), User::getAccount, request.getAccount())
                .eq(Objects.nonNull(request.getStatus()), User::getStatus, request.getStatus());

        this.page(page, queryWrapper);

        /*
         * 转换为DTO分页
         */
        Page<UserResponse> responsePage = new Page<>();

        BeanUtils.copyProperties(page, responsePage, "records");

        responsePage.setRecords(page.getRecords().stream().map(UserResponse::of).collect(Collectors.toList()));

        return responsePage;
    }

    @Override
    public void create(CreateUserRequest request) {
        if (this.existsByAccount(request.getAccount(), null)) {
            throw new BusinessException(BusinessException.Message.USER_EXISTS);
        }

        User user = new User();

        user.setNickname(request.getNickname());
        user.setAccount(request.getAccount());
        user.setPassword(PasswordUtil.encrypt(request.getPassword()));
        user.setStatus(request.getStatus());
        user.setCreateUserId(this.loginUtil.getLoginInfo().getUserId());
        user.setCreateDateTime(LocalDateTime.now());

        if (!this.save(user)) {
            throw new BusinessException(BusinessException.Message.CREATE_USER_FAIL);
        }
    }

    @Override
    public void modify(ModifyUserRequest request) {
        User user = this.getById(request.getId());

        if (Objects.isNull(user)) {
            throw new BusinessException(BusinessException.Message.USER_NOT_EXISTS);
        }

        if (this.existsByAccount(request.getAccount(), user.getId())) {
            throw new BusinessException(BusinessException.Message.USER_EXISTS);
        }

        user.setId(request.getId());
        user.setNickname(request.getNickname());
        user.setAccount(request.getAccount());
        user.setModifyUserId(this.loginUtil.getLoginInfo().getUserId());
        user.setModifyDateTime(LocalDateTime.now());

        if (!this.updateById(user)) {
            throw new BusinessException(BusinessException.Message.MODIFY_USER_FAIL);
        }
    }

    @Override
    public void modifyStatus(ModifyUserStatusRequest request) {
        User user = this.getById(request.getId());

        if (Objects.isNull(user)) {
            throw new BusinessException(BusinessException.Message.USER_NOT_EXISTS);
        }

        if (Objects.equals(user.getBasic(), BasicConstant.TRUE) && Objects.equals(request.getStatus(), UserStatusConstant.DISABLE)) {
            throw new BusinessException(BusinessException.Message.USER_CANNOT_DISABLE);
        }

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<User>()
                .eq(User::getId, request.getId())
                .set(User::getStatus, request.getStatus())
                .set(User::getModifyUserId, this.loginUtil.getLoginInfo().getUserId())
                .set(User::getModifyDateTime, LocalDateTime.now());

        if (!this.update(updateWrapper)) {
            throw new BusinessException(BusinessException.Message.MODIFY_USER_STATUS_FAIL);
        }
    }

    @Override
    public void modifyPassword(ModifyUserPasswordRequest request) {
        if (this.notExistsById(request.getId())) {
            throw new BusinessException(BusinessException.Message.USER_NOT_EXISTS);
        }

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<User>()
                .eq(User::getId, request.getId())
                .set(User::getPassword, PasswordUtil.encrypt(request.getPassword()))
                .set(User::getModifyUserId, this.loginUtil.getLoginInfo().getUserId())
                .set(User::getModifyDateTime, LocalDateTime.now());

        if (!this.update(updateWrapper)) {
            throw new BusinessException(BusinessException.Message.MODIFY_USER_PASSWORD_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(RemoveUserRequest request) {
        User user = this.getById(request.getId());

        if (Objects.isNull(user)) {
            throw new BusinessException(BusinessException.Message.USER_NOT_EXISTS);
        }

        if (Objects.equals(user.getBasic(), BasicConstant.TRUE)) {
            throw new BusinessException(BusinessException.Message.USER_CANNOT_DELETE);
        }

        if (!this.removeById(request.getId())) {
            throw new BusinessException(BusinessException.Message.REMOVE_USER_FAIL);
        }

        this.userRoleService.removeByUserId(request.getId());
    }

}