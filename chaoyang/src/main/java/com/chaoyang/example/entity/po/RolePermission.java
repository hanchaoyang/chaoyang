package com.chaoyang.example.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

/**
 * 角色权限实体类
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
//@Entity(name = "role_permission")
@TableName("role_permission")
@Data
public class RolePermission {

    /**
     * 主键
     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 角色主键
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 权限主键
     */
    @TableField("permission_id")
    private Long permissionId;

}