package com.chaoyang.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.entity.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 角色数据访问层接口
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID分页查询已关联的角色
     */
    @Select("SELECT role.* FROM role WHERE id IN (SELECT role_id FROM user_role WHERE user_id = #{userId})")
    Page<Role> selectAssociatedPage(@Param("userId") Long userId, @Param("page") Page<Role> page);

    /**
     * 根据用户ID分页查询未关联的角色
     */
    @Select("SELECT role.* FROM role WHERE id NOT IN (SELECT role_id FROM user_role WHERE user_id = #{userId})")
    Page<Role> selectUnassociatedPage(@Param("userId") Long userId, @Param("page") Page<Role> page);

}