/*
 Navicat Premium Data Transfer
 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : shop
 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001
 Date: 12/04/2022 14:45:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
    `id` bigint(20) UNSIGNED NOT NULL COMMENT '数据id',
    `t_user_id` bigint(20) NULL DEFAULT 0 COMMENT '用户id',
    `t_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户名',
    `t_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号',
    `t_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '地址',
    `t_total_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '总价格',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item`  (
     `id` bigint(20) UNSIGNED NOT NULL COMMENT '数据id',
     `t_order_id` bigint(20) NULL DEFAULT 0 COMMENT '订单id',
     `t_pro_id` bigint(20) NULL DEFAULT 0 COMMENT '商品id',
     `t_pro_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '商品名称',
     `t_pro_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '商品价格',
     `t_number` int(11) NULL DEFAULT 0 COMMENT '购买数量',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单条目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_item
-- ----------------------------

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product`  (
      `id` bigint(20) UNSIGNED NOT NULL COMMENT '数据id',
      `t_pro_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '商品名称',
      `t_pro_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '商品价格',
      `t_pro_stock` int(11) NULL DEFAULT 0 COMMENT '商品库存',
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES (1001, '华为', 2399.00, 100);
INSERT INTO `t_product` VALUES (1002, '小米', 1999.00, 100);
INSERT INTO `t_product` VALUES (1003, 'iphone', 4999.00, 100);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
       `id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '数据id',
       `t_username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户名',
       `t_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
       `t_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号',
       `t_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '地址',
       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1001, 'sijinghua', 'c26be8aaf53b15054896983b43eb6a65', '15107133391', '武汉');

SET FOREIGN_KEY_CHECKS = 1;
