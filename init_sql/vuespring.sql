/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据
 Source Server Type    : MySQL
 Source Server Version : 50742 (5.7.42)
 Source Host           : localhost:3306
 Source Schema         : vuespring

 Target Server Type    : MySQL
 Target Server Version : 50742 (5.7.42)
 File Encoding         : 65001

 Date: 16/03/2025 22:03:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
                             `role_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '角色名',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_request
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_request`;
CREATE TABLE `sys_role_request`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `role_id` int(11) NOT NULL COMMENT '角色表',
                                     `request_method` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '如GET、POST等，可以为空表示所有方法',
                                     `request_url` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'Ant风格的路径，如/api/**',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     INDEX `role_id`(`role_id`) USING BTREE,
                                     CONSTRAINT `sys_role_request_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_request
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
                             `last_modify_time` datetime NULL DEFAULT NULL,
                             `last_login_time` datetime NULL DEFAULT NULL,
                             `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `account_non_expired` tinyint(1) NULL DEFAULT NULL,
                             `account_non_locked` tinyint(1) NULL DEFAULT NULL,
                             `credentials_non_expired` tinyint(1) NULL DEFAULT NULL,
                             `enabled` tinyint(1) NULL DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1271713794 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (-1064501247, 'user123', '$2a$10$ssD1j70dZHtM8xStEHfLqerHikrEDGqjswmxBJSga4XjKADZouYHC', NULL, NULL, NULL, NULL, NULL, '2025-03-16 21:26:36', '2025-03-16 21:26:36', '2025-03-16 21:26:36', NULL, 0, 0, 0, 1);
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$kTTi5BKPUTyBJbQskRNj/O5h/1wdPizRskq3LCTiWHtJMfDvF.u96', '123@qq.com', '管理员', '12345678910', '上海浦东', NULL, '2023-02-07 00:59:50', NULL, '2025-03-16 13:21:06', NULL, 1, 1, 1, 1);
INSERT INTO `sys_user` VALUES (2, 'approximate', NULL, '123@qq.com', '大概', '12345678911', '上海浦东', NULL, '2023-04-13 07:13:45', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (1271713793, 'user', '$2a$10$gsZjkmQ3sfrJVF.tAwJzpOrgrrks0sVpLlCIXjUa3brBNqxYHJQwW', NULL, NULL, NULL, NULL, NULL, '2025-03-16 21:20:35', '2025-03-16 21:20:35', '2025-03-16 21:20:35', NULL, 0, 0, 0, 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
                                  `user_id` int(11) NOT NULL COMMENT '用户id',
                                  `role_id` int(11) NOT NULL COMMENT '角色id',
                                  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
                                  INDEX `role_id`(`role_id`) USING BTREE,
                                  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------

DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`  (
                                 `id` int(36) NOT NULL,
                                 `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
                                 `job_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务分组',
                                 `job_status` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '任务状态 是否启动任务',
                                 `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
                                 `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
                                 `bean_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务执行时调用哪个类的方法 包名+类名',
                                 `is_concurrent` tinyint(1) NULL DEFAULT NULL COMMENT '任务是否有状态',
                                 `spring_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'spring bean',
                                 `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务调用的方法名',
                                 `create_time` datetime NULL DEFAULT NULL,
                                 `last_modify_time` datetime NULL DEFAULT NULL,
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
