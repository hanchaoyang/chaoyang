package com.chaoyang.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaoyang.example.entity.po.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限数据访问层接口
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

}