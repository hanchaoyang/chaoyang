package com.chaoyang.example.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chaoyang.example.constant.BasicConstant;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色权限关联实体类
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@TableName("role_permission")
@Data
public class RolePermission {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 权限ID
     */
    @TableField("permission_id")
    private Long permissionId;

    /**
     * 是否是基础数据
     *
     * @see BasicConstant
     */
    @TableField("basic")
    private Integer basic;

    /**
     * 创建用户ID
     */
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField("create_date_time")
    private LocalDateTime createDateTime;

}