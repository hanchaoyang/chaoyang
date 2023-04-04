CREATE TABLE `user` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `nickname` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `password` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `phone` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `status` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `index_phone` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `user_role` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `role_id` bigint DEFAULT NULL,
    `user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `role` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `code` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `role_permission` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `permission_id` bigint DEFAULT NULL,
    `role_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `permission` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `code` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `chaoyang`.`user`(`id`, `nickname`, `password`, `phone`, `status`) VALUES (1, '朝阳', '123456', '18888888888', 1);

INSERT INTO `chaoyang`.`role`(`id`, `code`, `name`) VALUES (1, 'super', '超级管理员');
INSERT INTO `chaoyang`.`role`(`id`, `code`, `name`) VALUES (2, 'admin', '管理员');

INSERT INTO `chaoyang`.`permission`(`id`, `code`, `name`) VALUES (1, 'user:find', '查询用户');
INSERT INTO `chaoyang`.`permission`(`id`, `code`, `name`) VALUES (2, 'user:create', '添加用户');
INSERT INTO `chaoyang`.`permission`(`id`, `code`, `name`) VALUES (3, 'user:modify', '修改用户');
INSERT INTO `chaoyang`.`permission`(`id`, `code`, `name`) VALUES (4, 'user:remove', '删除用户');

INSERT INTO `chaoyang`.`user_role`(`role_id`, `user_id`) VALUES (1, 1);

INSERT INTO `chaoyang`.`role_permission`(`permission_id`, `role_id`) VALUES (1, 1);
INSERT INTO `chaoyang`.`role_permission`(`permission_id`, `role_id`) VALUES (2, 1);
INSERT INTO `chaoyang`.`role_permission`(`permission_id`, `role_id`) VALUES (3, 1);
INSERT INTO `chaoyang`.`role_permission`(`permission_id`, `role_id`) VALUES (4, 1);