package com.chaoyang.example.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chaoyang.example.constant.BasicConstant;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色实体类
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@TableName("role")
@Data
public class Role {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 标识
     */
    @TableField("code")
    private String code;

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

    /**
     * 最后修改用户ID
     */
    @TableField("modify_user_id")
    private Long modifyUserId;

    /**
     * 最后修改时间
     */
    @TableField("modify_date_time")
    private LocalDateTime modifyDateTime;

}