package com.chaoyang.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoyang.example.entity.po.User;
import com.chaoyang.example.mapper.UserMapper;
import com.chaoyang.example.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务层实现类
 *
 * @author 韩朝阳
 * @since 2023/3/17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {



}