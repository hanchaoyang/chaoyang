package com.chaoyang.example.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色权限实体类
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@TableName("role_permission")
@Data
public class RolePermission {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色主键
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 权限主键
     */
    @TableField("user_id")
    private Long permissionId;

}