package com.chaoyang.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoyang.example.entity.po.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 权限数据访问层接口
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据角色ID分页查询已关联的权限
     */
    @Select("SELECT permission.* FROM permission WHERE id IN (SELECT permission_id FROM role_permission WHERE role_id = #{roleId})")
    Page<Permission> selectAssociatedPage(@Param("roleId") Long roleId, @Param("page") Page<Permission> page);

    /**
     * 根据角色ID分页查询未关联的权限
     */
    @Select("SELECT permission.* FROM permission WHERE id NOT IN (SELECT permission_id FROM role_permission WHERE role_id = #{roleId})")
    Page<Permission> selectUnassociatedPage(@Param("roleId") Long roleId, @Param("page") Page<Permission> page);

}