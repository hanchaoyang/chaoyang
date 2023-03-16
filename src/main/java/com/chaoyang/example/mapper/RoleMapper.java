package com.chaoyang.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaoyang.example.entity.po.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色数据访问层接口
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}