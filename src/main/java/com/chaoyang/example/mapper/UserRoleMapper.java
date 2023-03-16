package com.chaoyang.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaoyang.example.entity.po.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色数据访问层接口
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}