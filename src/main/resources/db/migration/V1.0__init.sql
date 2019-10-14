
/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 13/10/2019 12:34:43
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT 0,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (1, 0, 'Title2', 'Google Chrome', NULL);
INSERT INTO `blog` VALUES (3, 0, 'go update', 'go update content', NULL);
INSERT INTO `blog` VALUES (4, 0, 'hantis', 'Kindle', NULL);
INSERT INTO `blog` VALUES (7, 0, 'hantis.. 88888 ', 'hantis update... 8888', NULL);
INSERT INTO `blog` VALUES (8, 0, 'testTitle8', 'testContnt8', NULL);
INSERT INTO `blog` VALUES (9, 122, 'hantis', 'hantis_content', '2018-11-13 13:26:41');
INSERT INTO `blog` VALUES (10, 122, 'hantis_t', 'hantis_content_t', '2018-11-13 13:26:41');
INSERT INTO `blog` VALUES (11, 122, 'hantis_333', 'hantis_content', '2018-12-02 02:50:51');
INSERT INTO `blog` VALUES (13, 123, 'go', 'go content', NULL);
INSERT INTO `blog` VALUES (14, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:33:52');
INSERT INTO `blog` VALUES (15, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:35:07');
INSERT INTO `blog` VALUES (16, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 11:46:59');
INSERT INTO `blog` VALUES (17, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 12:18:57');

-- ----------------------------
-- Table structure for blog1
-- ----------------------------
DROP TABLE IF EXISTS `blog1`;
CREATE TABLE `blog1`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT 0,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog1
-- ----------------------------
INSERT INTO `blog1` VALUES (1, 0, 'Title2', 'Google Chrome', NULL);
INSERT INTO `blog1` VALUES (3, 0, 'go update', 'go update content', NULL);
INSERT INTO `blog1` VALUES (4, 0, 'hantis', 'Kindle', NULL);
INSERT INTO `blog1` VALUES (7, 0, 'hantis.. 88888 ', 'hantis update... 8888', NULL);
INSERT INTO `blog1` VALUES (8, 0, 'testTitle8', 'testContnt8', NULL);
INSERT INTO `blog1` VALUES (9, 122, 'hantis', 'hantis_content', '2018-11-13 13:26:41');
INSERT INTO `blog1` VALUES (10, 122, 'hantis_t', 'hantis_content_t', '2018-11-13 13:26:41');
INSERT INTO `blog1` VALUES (11, 122, 'hantis_333', 'hantis_content', '2018-12-02 02:50:51');
INSERT INTO `blog1` VALUES (13, 123, 'go', 'go content', NULL);
INSERT INTO `blog1` VALUES (14, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:33:52');
INSERT INTO `blog1` VALUES (15, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:35:07');
INSERT INTO `blog1` VALUES (16, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 11:46:59');
INSERT INTO `blog1` VALUES (17, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 12:18:57');

-- ----------------------------
-- Table structure for blog_scores
-- ----------------------------
DROP TABLE IF EXISTS `blog_scores`;
CREATE TABLE `blog_scores`  (
  `id` int(11) NOT NULL DEFAULT 0,
  `user_id` int(11) NULL DEFAULT 0,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `Score` decimal(3, 2) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_scores
-- ----------------------------
INSERT INTO `blog_scores` VALUES (1, 0, 'Title2', 'Google Chrome', NULL, 3.50);
INSERT INTO `blog_scores` VALUES (1, 0, 'Title2', 'Google Chrome', NULL, 3.65);
INSERT INTO `blog_scores` VALUES (1, 0, 'Title2', 'Google Chrome', NULL, 4.00);
INSERT INTO `blog_scores` VALUES (1, 0, 'Title2', 'Google Chrome', NULL, 3.85);
INSERT INTO `blog_scores` VALUES (1, 0, 'Title2', 'Google Chrome', NULL, 4.00);
INSERT INTO `blog_scores` VALUES (1, 0, 'Title2', 'Google Chrome', NULL, 3.65);
INSERT INTO `blog_scores` VALUES (3, 0, 'go update', 'go update content', NULL, 3.50);
INSERT INTO `blog_scores` VALUES (3, 0, 'go update', 'go update content', NULL, 3.65);
INSERT INTO `blog_scores` VALUES (3, 0, 'go update', 'go update content', NULL, 4.00);
INSERT INTO `blog_scores` VALUES (3, 0, 'go update', 'go update content', NULL, 3.85);
INSERT INTO `blog_scores` VALUES (3, 0, 'go update', 'go update content', NULL, 4.00);
INSERT INTO `blog_scores` VALUES (3, 0, 'go update', 'go update content', NULL, 3.65);
INSERT INTO `blog_scores` VALUES (4, 0, 'hantis', 'Kindle', NULL, 3.50);
INSERT INTO `blog_scores` VALUES (4, 0, 'hantis', 'Kindle', NULL, 3.65);
INSERT INTO `blog_scores` VALUES (4, 0, 'hantis', 'Kindle', NULL, 4.00);
INSERT INTO `blog_scores` VALUES (4, 0, 'hantis', 'Kindle', NULL, 3.85);
INSERT INTO `blog_scores` VALUES (4, 0, 'hantis', 'Kindle', NULL, 4.00);
INSERT INTO `blog_scores` VALUES (4, 0, 'hantis', 'Kindle', NULL, 3.65);
INSERT INTO `blog_scores` VALUES (7, 0, 'hantis.. 88888 ', 'hantis update... 8888', NULL, 3.50);
INSERT INTO `blog_scores` VALUES (7, 0, 'hantis.. 88888 ', 'hantis update... 8888', NULL, 3.65);
INSERT INTO `blog_scores` VALUES (7, 0, 'hantis.. 88888 ', 'hantis update... 8888', NULL, 4.00);
INSERT INTO `blog_scores` VALUES (7, 0, 'hantis.. 88888 ', 'hantis update... 8888', NULL, 3.85);
INSERT INTO `blog_scores` VALUES (7, 0, 'hantis.. 88888 ', 'hantis update... 8888', NULL, 4.00);
INSERT INTO `blog_scores` VALUES (7, 0, 'hantis.. 88888 ', 'hantis update... 8888', NULL, 3.65);
INSERT INTO `blog_scores` VALUES (8, 0, 'testTitle8', 'testContnt8', NULL, 3.50);
INSERT INTO `blog_scores` VALUES (8, 0, 'testTitle8', 'testContnt8', NULL, 3.65);
INSERT INTO `blog_scores` VALUES (8, 0, 'testTitle8', 'testContnt8', NULL, 4.00);
INSERT INTO `blog_scores` VALUES (8, 0, 'testTitle8', 'testContnt8', NULL, 3.85);
INSERT INTO `blog_scores` VALUES (8, 0, 'testTitle8', 'testContnt8', NULL, 4.00);
INSERT INTO `blog_scores` VALUES (8, 0, 'testTitle8', 'testContnt8', NULL, 3.65);
INSERT INTO `blog_scores` VALUES (9, 122, 'hantis', 'hantis_content', '2018-11-13 13:26:41', 3.50);
INSERT INTO `blog_scores` VALUES (9, 122, 'hantis', 'hantis_content', '2018-11-13 13:26:41', 3.65);
INSERT INTO `blog_scores` VALUES (9, 122, 'hantis', 'hantis_content', '2018-11-13 13:26:41', 4.00);
INSERT INTO `blog_scores` VALUES (9, 122, 'hantis', 'hantis_content', '2018-11-13 13:26:41', 3.85);
INSERT INTO `blog_scores` VALUES (9, 122, 'hantis', 'hantis_content', '2018-11-13 13:26:41', 4.00);
INSERT INTO `blog_scores` VALUES (9, 122, 'hantis', 'hantis_content', '2018-11-13 13:26:41', 3.65);
INSERT INTO `blog_scores` VALUES (10, 122, 'hantis_t', 'hantis_content_t', '2018-11-13 13:26:41', 3.50);
INSERT INTO `blog_scores` VALUES (10, 122, 'hantis_t', 'hantis_content_t', '2018-11-13 13:26:41', 3.65);
INSERT INTO `blog_scores` VALUES (10, 122, 'hantis_t', 'hantis_content_t', '2018-11-13 13:26:41', 4.00);
INSERT INTO `blog_scores` VALUES (10, 122, 'hantis_t', 'hantis_content_t', '2018-11-13 13:26:41', 3.85);
INSERT INTO `blog_scores` VALUES (10, 122, 'hantis_t', 'hantis_content_t', '2018-11-13 13:26:41', 4.00);
INSERT INTO `blog_scores` VALUES (10, 122, 'hantis_t', 'hantis_content_t', '2018-11-13 13:26:41', 3.65);
INSERT INTO `blog_scores` VALUES (11, 122, 'hantis_333', 'hantis_content', '2018-12-02 02:50:51', 3.50);
INSERT INTO `blog_scores` VALUES (11, 122, 'hantis_333', 'hantis_content', '2018-12-02 02:50:51', 3.65);
INSERT INTO `blog_scores` VALUES (11, 122, 'hantis_333', 'hantis_content', '2018-12-02 02:50:51', 4.00);
INSERT INTO `blog_scores` VALUES (11, 122, 'hantis_333', 'hantis_content', '2018-12-02 02:50:51', 3.85);
INSERT INTO `blog_scores` VALUES (11, 122, 'hantis_333', 'hantis_content', '2018-12-02 02:50:51', 4.00);
INSERT INTO `blog_scores` VALUES (11, 122, 'hantis_333', 'hantis_content', '2018-12-02 02:50:51', 3.65);
INSERT INTO `blog_scores` VALUES (13, 123, 'go', 'go content', NULL, 3.50);
INSERT INTO `blog_scores` VALUES (13, 123, 'go', 'go content', NULL, 3.65);
INSERT INTO `blog_scores` VALUES (13, 123, 'go', 'go content', NULL, 4.00);
INSERT INTO `blog_scores` VALUES (13, 123, 'go', 'go content', NULL, 3.85);
INSERT INTO `blog_scores` VALUES (13, 123, 'go', 'go content', NULL, 4.00);
INSERT INTO `blog_scores` VALUES (13, 123, 'go', 'go content', NULL, 3.65);
INSERT INTO `blog_scores` VALUES (14, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:33:52', 3.50);
INSERT INTO `blog_scores` VALUES (14, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:33:52', 3.65);
INSERT INTO `blog_scores` VALUES (14, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:33:52', 4.00);
INSERT INTO `blog_scores` VALUES (14, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:33:52', 3.85);
INSERT INTO `blog_scores` VALUES (14, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:33:52', 4.00);
INSERT INTO `blog_scores` VALUES (14, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:33:52', 3.65);
INSERT INTO `blog_scores` VALUES (15, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:35:07', 3.50);
INSERT INTO `blog_scores` VALUES (15, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:35:07', 3.65);
INSERT INTO `blog_scores` VALUES (15, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:35:07', 4.00);
INSERT INTO `blog_scores` VALUES (15, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:35:07', 3.85);
INSERT INTO `blog_scores` VALUES (15, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:35:07', 4.00);
INSERT INTO `blog_scores` VALUES (15, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 00:35:07', 3.65);
INSERT INTO `blog_scores` VALUES (16, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 11:46:59', 3.50);
INSERT INTO `blog_scores` VALUES (16, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 11:46:59', 3.65);
INSERT INTO `blog_scores` VALUES (16, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 11:46:59', 4.00);
INSERT INTO `blog_scores` VALUES (16, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 11:46:59', 3.85);
INSERT INTO `blog_scores` VALUES (16, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 11:46:59', 4.00);
INSERT INTO `blog_scores` VALUES (16, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 11:46:59', 3.65);
INSERT INTO `blog_scores` VALUES (17, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 12:18:57', 3.50);
INSERT INTO `blog_scores` VALUES (17, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 12:18:57', 3.65);
INSERT INTO `blog_scores` VALUES (17, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 12:18:57', 4.00);
INSERT INTO `blog_scores` VALUES (17, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 12:18:57', 3.85);
INSERT INTO `blog_scores` VALUES (17, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 12:18:57', 4.00);
INSERT INTO `blog_scores` VALUES (17, 21, '攻壳机动队', '攻壳机动队 XXXX', '2018-12-29 12:18:57', 3.65);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `Id` int(11) NULL DEFAULT NULL,
  `Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Salary` int(11) NULL DEFAULT NULL,
  `ManagerId` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, 'Joe', 70000, 3);
INSERT INTO `employee` VALUES (2, 'Henry', 80000, 4);
INSERT INTO `employee` VALUES (3, 'Sam', 60000, NULL);
INSERT INTO `employee` VALUES (4, 'Max', 90000, NULL);

-- ----------------------------
-- Table structure for log_partition
-- ----------------------------
DROP TABLE IF EXISTS `log_partition`;
CREATE TABLE `log_partition`  (
  `dt` datetime(0) NOT NULL,
  `info` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  INDEX `dt`(`dt`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic PARTITION BY RANGE (year(`dt`))
PARTITIONS 6
(PARTITION `p0` VALUES LESS THAN (2005) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p1` VALUES LESS THAN (2006) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p2` VALUES LESS THAN (2007) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p3` VALUES LESS THAN (2008) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p4` VALUES LESS THAN (2009) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p5` VALUES LESS THAN (MAXVALUE) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 )
;

-- ----------------------------
-- Records of log_partition
-- ----------------------------
INSERT INTO `log_partition` VALUES ('2006-05-06 15:34:23', 'cadsts');

-- ----------------------------
-- Table structure for logs
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs`  (
  `Id` int(11) NULL DEFAULT NULL,
  `Num` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of logs
-- ----------------------------
INSERT INTO `logs` VALUES (1, 1);
INSERT INTO `logs` VALUES (2, 1);
INSERT INTO `logs` VALUES (3, 1);
INSERT INTO `logs` VALUES (4, 2);
INSERT INTO `logs` VALUES (5, 1);
INSERT INTO `logs` VALUES (6, 2);
INSERT INTO `logs` VALUES (7, 2);

-- ----------------------------
-- Table structure for scores
-- ----------------------------
DROP TABLE IF EXISTS `scores`;
CREATE TABLE `scores`  (
  `Id` int(11) NULL DEFAULT NULL,
  `Score` decimal(3, 2) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of scores
-- ----------------------------
INSERT INTO `scores` VALUES (1, 3.50);
INSERT INTO `scores` VALUES (2, 3.65);
INSERT INTO `scores` VALUES (3, 4.00);
INSERT INTO `scores` VALUES (4, 3.85);
INSERT INTO `scores` VALUES (3, 4.00);
INSERT INTO `scores` VALUES (6, 3.65);

SET FOREIGN_KEY_CHECKS = 1;
