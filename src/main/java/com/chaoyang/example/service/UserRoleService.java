package com.chaoyang.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoyang.example.entity.po.Role;
import com.chaoyang.example.entity.po.UserRole;

import java.util.List;
import java.util.Set;

/**
 * 用户角色服务层接口
 *
 * @author 韩朝阳
 * @since 2023/3/19
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 根据用户主键查询角色
     */
    List<UserRole> findByUserId(Long userId);

    void removeByUserId(Long userId);

    void removeByRoleId(Long roleId);

}