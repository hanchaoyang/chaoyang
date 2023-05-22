package com.chaoyang.example.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chaoyang.example.constant.BasicConstant;
import com.chaoyang.example.constant.UserStatusConstant;
import lombok.Data;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

/**
 * 用户实体类
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
//@Entity(name = "user")
@TableName("user")
@Data
public class User {

    /**
     * 主键
     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 状态
     *
     * @see UserStatusConstant
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否是基础数据
     *
     * @see BasicConstant
     */
    @TableField("basic")
    private Integer basic;

}