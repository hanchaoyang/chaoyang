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
 * 用户角色实体类
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
//@Entity(name = "user_role")
@TableName("user_role")
@Data
public class UserRole {

    /**
     * 主键
     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户主键
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色主键
     */
    @TableField("role_id")
    private Long roleId;

}