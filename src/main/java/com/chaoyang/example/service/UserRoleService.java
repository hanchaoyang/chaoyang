package com.chaoyang.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoyang.example.entity.po.UserRole;

/**
 * 用户角色服务层接口
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
public interface UserRoleService extends IService<UserRole> {

    void removeByUserId(Long userId);

    void removeByRoleId(Long roleId);

}