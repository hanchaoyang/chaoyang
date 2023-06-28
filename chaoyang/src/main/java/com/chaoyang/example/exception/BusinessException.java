package com.chaoyang.example.exception;

/**
 * 业务异常
 *
 * @author 韩朝阳
 * @since 2023/3/20
 */
public class BusinessException extends RuntimeException {

    /**
     * 响应码，5XX，用户可读的错误提示
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static final int code = 500;

    public BusinessException() {

    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static int getCode() {
        return BusinessException.code;
    }

    /**
     * 业务异常响应消息常量
     */
    public static class Message {

        public static final String USER_NOT_EXISTS = "用户不存在";

        public static final String USER_EXISTS = "用户已存在";

        public static final String CREATE_USER_FAIL = "添加用户失败";

        public static final String MODIFY_USER_FAIL = "修改用户失败";

        public static final String USER_CANNOT_DISABLE = "用户不能被禁用";

        public static final String MODIFY_USER_STATUS_FAIL = "修改用户状态失败";

        public static final String MODIFY_USER_PASSWORD_FAIL = "修改用户密码失败";

        public static final String USER_CANNOT_DELETE = "用户不能被删除";

        public static final String REMOVE_USER_FAIL = "删除用户失败";


        public static final String ROLE_NOT_EXISTS = "角色不存在";

        public static final String ROLE_NAME_OR_CODE_EXISTS = "角色名称或标识已存在";

        public static final String CREATE_ROLE_FAIL = "添加角色失败";

        public static final String ROLE_CANNOT_MODIFY = "角色不能被修改";

        public static final String MODIFY_ROLE_FAIL = "修改角色失败";

        public static final String ROLE_CANNOT_REMOVE = "角色不能被删除";

        public static final String REMOVE_ROLE_FAIL = "删除角色失败";


        public static final String PERMISSION_NOT_EXISTS = "权限不存在";

        public static final String PERMISSION_NAME_OR_CODE_EXISTS = "权限名称或标识已存在";

        public static final String CREATE_PERMISSION_FAIL = "添加权限失败";

        public static final String PERMISSION_CANNOT_MODIFY = "权限不能被修改";

        public static final String MODIFY_PERMISSION_FAIL = "修改权限失败";

        public static final String PERMISSION_CANNOT_REMOVE = "权限不能被删除";

        public static final String REMOVE_PERMISSION_FAIL = "删除权限失败";


        public static final String USER_ROLE_NOT_EXISTS = "用户角色关联不存在";

        public static final String USER_ROLE_EXISTS = "用户角色关联已存在";

        public static final String CREATE_USER_ROLE_FAIL = "添加用户角色关联失败";

        public static final String USER_ROLE_CANNOT_REMOVE = "用户角色关联不能被删除";

        public static final String REMOVE_USER_ROLE_FAIL = "删除用户角色关联失败";


        public static final String ROLE_PERMISSION_NOT_EXISTS = "角色权限关联不存在";

        public static final String ROLE_PERMISSION_EXISTS = "角色权限关联已存在";

        public static final String CREATE_ROLE_PERMISSION_FAIL = "添加角色权限关联失败";

        public static final String ROLE_PERMISSION_CANNOT_REMOVE = "角色权限关联不能被删除";

        public static final String REMOVE_ROLE_PERMISSION_FAIL = "删除角色权限关联失败";

    }

}