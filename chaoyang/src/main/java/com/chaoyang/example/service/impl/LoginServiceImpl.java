package com.chaoyang.example.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.util.IdUtil;
import com.chaoyang.example.constant.UserStatusConstant;
import com.chaoyang.example.entity.dto.LoginInfo;
import com.chaoyang.example.entity.dto.request.GetCaptchaRequest;
import com.chaoyang.example.entity.dto.request.LoginRequest;
import com.chaoyang.example.entity.dto.response.LoginInfoResponse;
import com.chaoyang.example.entity.dto.response.LoginResponse;
import com.chaoyang.example.entity.po.*;
import com.chaoyang.example.exception.AuthenticationException;
import com.chaoyang.example.exception.ParameterException;
import com.chaoyang.example.service.*;
import com.chaoyang.example.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 登录服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final LoginUtil loginUtil;

    private final RedisService redisService;

    private final UserService userService;

    private final UserRoleService userRoleService;

    private final RoleService roleService;

    private final RolePermissionService rolePermissionService;

    private final PermissionService permissionService;

    @Override
    public void getCaptcha(GetCaptchaRequest request, HttpServletResponse httpResponse) {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(130, 40, 4, 0);

        this.redisService.setCaptcha(request.getNonce(), captcha.getCode().toLowerCase(Locale.ROOT), Duration.ofMinutes(1L));

        try {
            captcha.write(httpResponse.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        /*
         * 验证验证码
         */
        String nonce = request.getNonce();

        String realCaptcha = this.redisService.getCaptcha(nonce.toLowerCase(Locale.ROOT));

        if (Objects.isNull(realCaptcha)) {
            throw new AuthenticationException(AuthenticationException.Message.CAPTCHA_ERROR);
        }

        if (Objects.equals(request.getCaptcha(), realCaptcha)) {
            this.redisService.deleteCaptcha(nonce);
        } else {
            throw new AuthenticationException(AuthenticationException.Message.CAPTCHA_ERROR);
        }

        /*
         * 验证用户账号、密码、状态
         */
        User user = this.userService.findByAccountAndPassword(request.getAccount(), request.getPassword());

        if (Objects.isNull(user)) {
            throw new AuthenticationException(AuthenticationException.Message.USER_NOT_EXISTS_OR_PASSWORD_ERROR);
        }

        if (Objects.equals(user.getStatus(), UserStatusConstant.DISABLE)) {
            throw new AuthenticationException(AuthenticationException.Message.USER_DISABLED);
        }

        /*
         * 生成Token和过期时间
         */
        String token = IdUtil.simpleUUID();

        Duration expire = Duration.ofDays(30);

        Long userId = user.getId();

        /*
         * 踢掉前面的登录
         */
        LinkedList<String> tokens = (LinkedList<String>) this.redisService.getTokens(userId);

        if (Objects.isNull(tokens)) {
            tokens = new LinkedList<>();
        } else {
            // 这个值表示允许一个用户同时在几处登录，如果为1，则不允许同时登录
            if (tokens.size() >= 1) {
                this.redisService.deleteUserId(tokens.removeFirst());
            }
        }

        tokens.add(token);

        this.redisService.setTokens(userId, tokens, expire);

        /*
         * 缓存用户ID
         */
        this.redisService.setUserId(token, userId, expire);

        /*
         * 缓存登录信息（用户、角色、权限）
         */
        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setUserId(user.getId());
        loginInfo.setNickname(user.getNickname());
        loginInfo.setAccount(user.getAccount());

        List<UserRole> userRoles = this.userRoleService.findByUserId(userId);

        if (!userRoles.isEmpty()) {
            List<Role> roles = this.roleService.findByIds(userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()));

            loginInfo.setRoles(roles.stream().map(Role::getCode).collect(Collectors.toSet()));

            List<RolePermission> rolePermissions = this.rolePermissionService.findByRoleIds(roles.stream().map(Role::getId).collect(Collectors.toList()));

            if (!rolePermissions.isEmpty()) {
                List<Permission> permissions = this.permissionService.findByIds(rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList()));

                loginInfo.setPermissions(permissions.stream().map(Permission::getCode).collect(Collectors.toSet()));
            }
        }

        this.redisService.setLoginInfo(userId, loginInfo, expire);

        // 返回Token
        return LoginResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void logout() {
        String currentToken = this.loginUtil.getToken();

        if (Objects.isNull(currentToken)) {
            throw new ParameterException(ParameterException.Message.NO_TOKEN);
        }

        Long userId = this.redisService.getUserId(currentToken);

        if (Objects.isNull(userId)) {
            throw new AuthenticationException(AuthenticationException.Message.NOT_LOGIN);
        }

        this.redisService.deleteUserId(currentToken);

        List<String> tokens = this.redisService.getTokens(userId);

        tokens = tokens.stream().filter(token -> !Objects.equals(token, currentToken)).collect(Collectors.toList());

        if (tokens.isEmpty()) {
            this.redisService.deleteTokens(userId);
            this.redisService.deleteLoginInfo(userId);
        } else {
            this.redisService.setTokens(userId, tokens);
        }
    }

    @Override
    public LoginInfoResponse getLoginInfo() {
        return LoginInfoResponse.of(this.loginUtil.getLoginInfo());
    }

}