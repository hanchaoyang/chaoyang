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