package com.chaoyang.example.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSONObject;
import com.chaoyang.example.constant.UserStatusConstant;
import com.chaoyang.example.entity.dto.LoginInfo;
import com.chaoyang.example.entity.dto.request.GetQrCodeRequest;
import com.chaoyang.example.entity.dto.request.LoginRequest;
import com.chaoyang.example.entity.dto.response.LoginResponse;
import com.chaoyang.example.entity.po.*;
import com.chaoyang.example.exception.AuthException;
import com.chaoyang.example.exception.BusinessException;
import com.chaoyang.example.exception.ParameterException;
import com.chaoyang.example.service.*;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 登录服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */

/**
 * 判断redis调用是否成功，否则会出现像假token请求退出接口返回成功的情况
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final RedisTemplate<String, String> redisTemplate;

    private final UserService userService;

    private final UserRoleService userRoleService;

    private final RoleService roleService;

    private final RolePermissionService rolePermissionService;

    private final PermissionService permissionService;

    @Override
    public void getQrCode(GetQrCodeRequest getQrCodeRequest, HttpServletResponse httpServletResponse) {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(120, 40, 2);

        String qrcode = captcha.text();

        this.redisTemplate.opsForValue().set(String.format("chaoyang:qrcode:%s", getQrCodeRequest.getNonce()), qrcode, Duration.ofMinutes(1L));

        try {
            captcha.out(httpServletResponse.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO 重复登录问题
     */
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
//        String key = String.format("chaoyang:qrcode:%s", loginRequest.getNonce());
//
//        String qrcode = this.redisTemplate.opsForValue().get(key);
//
//        if (Objects.equals(loginRequest.getQrcode(), qrcode)) {
//            this.redisTemplate.delete(key);
//        } else {
//            throw new BusinessException("验证码错误");
//        }

        User user = this.userService.findByPhoneAndPassword(loginRequest.getUserPhone(), DigestUtil.md5Hex(DigestUtil.md5Hex(loginRequest.getUserPassword())));

        if (Objects.isNull(user)) {
            throw new AuthException("用户名或密码错误");
        }

        if (Objects.equals(user.getStatus(), UserStatusConstant.DISABLE)) {
            throw new AuthException("用户已禁用");
        }

        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setUserId(user.getId());
        loginInfo.setUserNickname(user.getNickname());
        loginInfo.setUserPhone(user.getPhone());

        List<UserRole> userRoles = this.userRoleService.findByUserId(user.getId());

        if (!userRoles.isEmpty()) {
            List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

            List<Role> roles = this.roleService.findByIds(roleIds);

            loginInfo.setUserRoles(roles.stream().map(Role::getCode).collect(Collectors.toSet()));

            List<RolePermission> rolePermissions = this.rolePermissionService.findByRoleIds(roleIds);

            if (!rolePermissions.isEmpty()) {
                List<Long> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

                List<Permission> permissions = this.permissionService.findByIds(permissionIds);

                loginInfo.setUserPermissions(permissions.stream().map(Permission::getCode).collect(Collectors.toSet()));
            }
        }

        String token = UUID.randomUUID().toString();

        this.redisTemplate.opsForValue().set(String.format("chaoyang:login-info:%s", token), JSONObject.toJSONString(loginInfo), Duration.ofDays(7L));

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setToken(token);

        return loginResponse;
    }

    @Override
    public void logout(String token) {
        if (Objects.isNull(token) || token.isEmpty()) {
            throw new ParameterException("参数错误");
        }

        this.redisTemplate.delete(String.format("chaoyang:login-info:%s", token));
    }

}