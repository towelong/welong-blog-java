/*
 Navicat Premium Data Transfer

 Source Server         : mysql8
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 11/05/2020 09:00:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标题',
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章描述',
  `image` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章图片',
  `status` tinyint(0) NOT NULL DEFAULT 1 COMMENT '是否上线',
  `views` int(0) NULL DEFAULT 0 COMMENT '阅读数',
  `like` int(0) NULL DEFAULT 0 COMMENT '喜欢数',
  `content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章内容',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '七月在夏天', '春天来了，秋天还会远吗？', 'https://resource.shirmy.me/blog/covers/2019-10-17/koa2-covers.png', 1, 0, 0, '春天来了，秋天还会远吗？应该不远了。', '2020-04-16 12:04:01', '2020-04-26 12:53:06', NULL);
INSERT INTO `article` VALUES (2, '枯木逢春', '枯木逢春，我逢谁？', 'https://resource.shirmy.me/blog/covers/2019-10-17/koa2-covers.png', 0, 0, 0, '枯木逢春，我逢谁？', '2020-04-16 12:13:49', '2020-04-26 17:07:37', NULL);
INSERT INTO `article` VALUES (3, 'MySQL', 'girls skir', 'https://resource.shirmy.me/blog/covers/2019-08-27/vue-jest.png', 1, 0, 0, '> 温馨提示：\r\n>\r\n> 在阅读本章内容前，请大概了解下文章顶部的两张表的结构以及数据内容。\r\n>\r\n> 若在下面的教程中对`SQL`执行结果持有疑问，返回顶部查看即可。\r\n\r\n### `employee表`\r\n\r\n``` \r\n+-------------+---------------+--------------+---------------+\r\n| employee_id | employee_name | employee_age | department_id |\r\n+-------------+---------------+--------------+---------------+\r\n|           1 | Lisa          |           21 |           100 |\r\n|           2 | Pony          |           40 |           101 |\r\n+-------------+---------------+--------------+---------------+\r\n```\r\n\r\n### `department表`\r\n\r\n```\r\n+---------------+-----------------+\r\n| department_id | department_name |\r\n+---------------+-----------------+\r\n|           100 | 技术部          |\r\n|           102 | 产品部          |\r\n|           103 | 宣传部          |\r\n|           105 | 公关部          |\r\n+---------------+-----------------+\r\n```\r\n\r\n\r\n\r\n### 全连接\r\n\r\n`全连接`是指两张表符合某一条件时，返回两张表的全部内容；不符合条件的字段则不返回任何值。\r\n\r\n在下面的例子中我们将讲到 `employee`和 `department`这两张表是通过 `department_id` 把两张表给连接起来的。\r\n\r\n`全连接`分为`INNER JOIN` 和 `WHERE` 两种查询方式，其中，`INNER JOIN`查询为显示的连接方式，而`WHERE` 查询为隐式的连接方式\r\n\r\n\r\n- `INNER JOIN`查询\r\n\r\n```mysql\r\nSELECT * FROM employee e INNER JOIN department d ON e.department_id = d.department_id\r\n```\r\n执行`SQL`得到如下结果：\r\n\r\n```\r\n+-------------+---------------+--------------+---------------+---------------+-----------------+\r\n| employee_id | employee_name | employee_age | department_id | department_id | department_name |\r\n+-------------+---------------+--------------+---------------+---------------+-----------------+\r\n|           1 | Lisa          |           21 |           100 |           100 | 技术部          |\r\n+-------------+---------------+--------------+---------------+---------------+-----------------+\r\n```\r\n\r\n\r\n\r\n- `WHERE` 查询\r\n\r\n```mysql\r\nSELECT * FROM employee,department WHERE employee.department_id = department.department_id\r\n```\r\n\r\n执行`SQL`得到如下结果：\r\n\r\n```\r\n+-------------+---------------+--------------+---------------+---------------+-----------------+\r\n| employee_id | employee_name | employee_age | department_id | department_id | department_name |\r\n+-------------+---------------+--------------+---------------+---------------+-----------------+\r\n|           1 | Lisa          |           21 |           100 |           100 | 技术部          |\r\n+-------------+---------------+--------------+---------------+---------------+-----------------+\r\n```\r\n\r\n`INNER JOIN` 和` WHERE` 两种`查询方式`等效，且性能相差无几（在小数据量的情况下测试）。\r\n\r\n###  外连接\r\n\r\n> 为了更好的说明和理解，我们做如下声明\r\n\r\n`外连接`可以根据保留表分为` 左外连接` 和  `右外连接`\r\n\r\n` 左外连接` 简称为`左连接`；\r\n\r\n`右外连接`简称为`右连接`；\r\n\r\n`外连接`并不要求两张表中的记录都能够匹配，即使没有匹配到也会保留数据；被保留全部数据的表被称为`保留表`。\r\n\r\n####  左连接\r\n\r\n- `LEFT JOIN`查询\r\n```mysql\r\nSELECT * FROM employee e LEFT JOIN department d ON e.department_id = d.department_id\r\n```\r\n\r\n\r\n执行`SQL`得到：\r\n\r\n```\r\n+-------------+---------------+--------------+---------------+---------------+-----------------+\r\n| employee_id | employee_name | employee_age | department_id | department_id | department_name |\r\n+-------------+---------------+--------------+---------------+---------------+-----------------+\r\n|           1 | Lisa          |           21 |           100 |           100 | 技术部          |\r\n|           2 | Pony          |           40 |           101 |          NULL | NULL           |\r\n+-------------+---------------+--------------+---------------+---------------+-----------------+\r\n```\r\n\r\n左表`employee`为`保留表`，因此`employee`表被保留了全部数据，在`Pony`的记录当中，`department_id`为`101`，而在`department`表中并不存在`department_id`为101的记录，故`department_id`和`department_name`为`NULL`。\r\n\r\n#### 右连接\r\n\r\n- `RIGHT JOIN` 查询\r\n\r\n\r\n```mysql\r\n  SELECT * FROM employee e RIGHT JOIN department d ON e.department_id = d.department_id\r\n```\r\n\r\n\r\n  为了更好看，只查询了如下字段\r\n\r\n  执行`SQL`：\r\n\r\n  ```mysql\r\nSELECT e.employee_id,e.employee_name,d.department_id,d.department_name FROM employee e RIGHT JOIN department d ON e.department_id = d.department_id\r\n  ```\r\n\r\n  得到：\r\n\r\n  ```\r\n+-------------+---------------+---------------+-----------------+\r\n| employee_id | employee_name | department_id | department_name |\r\n+-------------+---------------+---------------+-----------------+\r\n|           1 | Lisa          |           100 | 技术部          |\r\n|        NULL | NULL          |           102 | 产品部          |\r\n|        NULL | NULL          |           103 | 宣传部          |\r\n|        NULL | NULL          |           105 | 公关部          |\r\n+-------------+---------------+---------------+-----------------+\r\n  ```\r\n\r\n右表`department`为`保留表`，但是在`department_id`为`102`,`103`,`105`中并没有找到其对应的`employee_name`以及`employee_id`，故不存在的字段为`NULL`。\r\n\r\n\r\n\r\n```mysql\r\n SELECT user_id,group_id,group_name,u.nickname,u.account,u.`password`,u.create_time,u.update_time,u.delete_time FROM user_group LEFT JOIN `user` u ON user_group.user_id = u.id \r\n LEFT JOIN `group` ON `group`.id = user_group.group_id\r\n WHERE user_id IN (SELECT id FROM `user`)\r\n```\r\n\r\n首先，获取到一组 user_id(user)\r\n\r\n通过user_id拿到一组group_id(user_group)\r\n\r\n再通过group_id拿到group_name(group)\r\n\r\n', '2020-04-19 14:29:22', '2020-04-26 13:02:08', NULL);
INSERT INTO `article` VALUES (4, '明月天涯', '你我执手仗剑天下', 'https://resource.shirmy.me/blog/covers/2019-10-17/koa2-covers.png', 1, 0, 0, '明月照我心', '2020-04-19 14:41:23', '2020-04-26 12:53:15', NULL);
INSERT INTO `article` VALUES (14, '琵琶行', '犹抱琵琶半遮面', 'https://resource.shirmy.me/blog/covers/2019-10-17/koa2-covers.png', 0, 0, 0, '琵琶行，白居易', '2020-04-19 15:51:02', '2020-04-26 20:26:46', '2020-04-26 20:26:47');
INSERT INTO `article` VALUES (15, '琵琶行', '犹抱琵琶半遮面', 'https://resource.shirmy.me/blog/covers/2019-10-17/koa2-covers.png', 1, 0, 0, '琵琶行，白居易', '2020-04-25 18:18:28', '2020-04-28 12:36:04', '2020-04-26 20:23:53');
INSERT INTO `article` VALUES (16, '林间有风', '林间有风 林间有风', 'https://s1.ax1x.com/2020/05/06/YEI78J.png', 1, 0, 0, '只有少数特别幸运的人生来就自信满满，多数的你我，一辈子，最幸运的事，就是遇见个把赏识你的人，把你于不自信的苍茫之中叫醒，让你发现，原来我也并非一无是处。', '2020-04-26 14:38:43', '2020-05-06 20:15:19', NULL);
INSERT INTO `article` VALUES (17, '《黑客与画家》', '硅谷创业之父——Paul Graham文集。', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588740861242&di=5c66275a27402fd1f26e61155ae54436&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180104%2F8852414cd23747f08a30bbaa01159370.jpeg', 1, 0, 0, '本书是硅谷创业之父Paul Graham 的文集，主要介绍黑客即优秀程序员的爱好和动机，讨论黑客成长、黑客对世界的贡献以及编程语言和黑客工作方法等所有对计算机时代感兴趣的人的一些话题。书中的内容不但有助于了解计算机编程的本质、互联网行业的规则，还会帮助读者了解我们这个时代，迫使读者独立思考。\n\n本书适合所有程序员和互联网创业者，也适合一切对计算机行业感兴趣的读者。', '2020-04-26 17:21:41', '2020-05-06 10:24:55', NULL);
INSERT INTO `article` VALUES (18, '如何优雅地操作DOM', '在以前，操作DOM是一件非常麻烦的事情  ', 'https://resource.shirmy.me/blog/covers/2019-09-08/DOM-manipulation-cover.png', 1, 0, 0, 'DOM操作是一件麻烦事，但现在我们有更强大的原生API来操作它，还能监听DOM树的变化，以及元素和视窗的交叉状态。', '2020-04-26 18:12:45', NULL, NULL);

-- ----------------------------
-- Table structure for article_user
-- ----------------------------
DROP TABLE IF EXISTS `article_user`;
CREATE TABLE `article_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `article_id` int(0) NOT NULL COMMENT '文章id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_user
-- ----------------------------
INSERT INTO `article_user` VALUES (1, 1, 1, '2020-04-16 12:04:14', NULL, NULL);
INSERT INTO `article_user` VALUES (2, 4, 2, '2020-04-16 12:14:16', '2020-04-18 17:37:44', NULL);
INSERT INTO `article_user` VALUES (4, 4, 14, '2020-04-19 15:51:02', '2020-04-26 20:26:46', '2020-04-26 20:26:47');
INSERT INTO `article_user` VALUES (5, 4, 15, '2020-04-25 18:18:28', '2020-04-26 20:23:53', '2020-04-26 20:23:53');
INSERT INTO `article_user` VALUES (6, 4, 16, '2020-04-26 14:38:43', NULL, NULL);
INSERT INTO `article_user` VALUES (7, 4, 17, '2020-04-26 17:21:41', NULL, NULL);
INSERT INTO `article_user` VALUES (8, 4, 18, '2020-04-26 18:12:45', NULL, NULL);

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `group_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分组名称',
  `scope` int(0) NOT NULL COMMENT '权限级别',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES (1, '测试组', 8, '2020-04-16 12:05:25', NULL, NULL);
INSERT INTO `group` VALUES (2, '管理组', 16, '2020-04-18 17:09:25', NULL, NULL);
INSERT INTO `group` VALUES (3, '用户组', 4, '2020-04-18 17:09:40', NULL, NULL);
INSERT INTO `group` VALUES (5, '内测组', 0, '2020-04-24 13:19:34', '2020-04-26 20:11:29', '2020-04-26 20:11:29');
INSERT INTO `group` VALUES (6, '花里胡哨组', 0, '2020-04-24 16:45:44', '2020-04-24 16:45:55', '2020-04-24 16:45:52');
INSERT INTO `group` VALUES (7, '体验组', 8, '2020-04-26 18:42:15', '2020-04-26 20:16:47', '2020-04-26 20:16:48');
INSERT INTO `group` VALUES (8, '公告组~', 2, '2020-04-26 18:47:38', '2020-04-26 19:03:59', NULL);
INSERT INTO `group` VALUES (9, '运维组', 3, '2020-04-26 20:29:13', NULL, NULL);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名称',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, '春天', '2020-04-16 12:05:51', '2020-04-26 19:16:54', '2020-04-26 19:16:55');
INSERT INTO `tag` VALUES (2, '代码', '2020-04-19 15:37:16', NULL, NULL);
INSERT INTO `tag` VALUES (4, '生活', '2020-04-19 15:59:33', '2020-04-26 19:20:58', '2020-04-26 19:20:59');
INSERT INTO `tag` VALUES (7, 'code1', '2020-04-21 12:41:34', '2020-04-22 11:51:26', '2020-04-22 11:51:27');
INSERT INTO `tag` VALUES (15, '旅游', '2020-04-21 13:39:27', NULL, NULL);
INSERT INTO `tag` VALUES (17, '录像', '2020-04-21 13:39:48', '2020-04-26 19:20:45', '2020-04-26 19:20:46');
INSERT INTO `tag` VALUES (18, '2020', '2020-04-21 14:36:12', NULL, NULL);
INSERT INTO `tag` VALUES (20, '诗词', '2020-04-21 14:43:56', NULL, NULL);
INSERT INTO `tag` VALUES (21, '豆瓣', '2020-04-21 14:53:46', '2020-04-26 19:19:21', '2020-04-26 19:19:22');
INSERT INTO `tag` VALUES (22, '测试', '2020-04-22 14:43:04', '2020-04-26 19:18:52', '2020-04-26 19:18:52');
INSERT INTO `tag` VALUES (23, '艺术', '2020-04-26 20:27:32', NULL, NULL);
INSERT INTO `tag` VALUES (24, '读后感', '2020-04-26 20:28:35', NULL, NULL);

-- ----------------------------
-- Table structure for tag_article
-- ----------------------------
DROP TABLE IF EXISTS `tag_article`;
CREATE TABLE `tag_article`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_id` int(0) NOT NULL COMMENT '标签id',
  `article_id` int(0) NOT NULL COMMENT '文章id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag_article
-- ----------------------------
INSERT INTO `tag_article` VALUES (1, 1, 1, '2020-04-16 12:05:40', '2020-04-26 19:16:54', '2020-04-26 19:16:54');
INSERT INTO `tag_article` VALUES (4, 20, 14, '2020-04-19 15:51:02', '2020-05-06 10:05:57', '2020-04-26 19:16:55');
INSERT INTO `tag_article` VALUES (5, 7, 2, '2020-04-22 11:18:46', '2020-04-22 11:51:22', '2020-04-22 11:51:22');
INSERT INTO `tag_article` VALUES (8, 17, 3, '2020-04-25 18:03:28', '2020-04-26 19:20:45', '2020-04-26 19:20:46');
INSERT INTO `tag_article` VALUES (9, 20, 15, '2020-04-25 18:18:28', '2020-04-26 18:15:27', NULL);
INSERT INTO `tag_article` VALUES (10, 23, 16, '2020-04-26 14:38:43', '2020-05-06 10:25:02', NULL);
INSERT INTO `tag_article` VALUES (11, 4, 4, '2020-04-26 15:53:16', '2020-04-26 19:20:58', '2020-04-26 19:20:59');
INSERT INTO `tag_article` VALUES (12, 2, 17, '2020-04-26 17:21:41', '2020-04-26 18:12:58', NULL);
INSERT INTO `tag_article` VALUES (13, 2, 18, '2020-04-26 18:12:45', NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `nickname` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '七月在夏天', 'summer@qq.com', '7yue', 'pbkdf2sha256:64000:18:24:n:lIiSVE8YQg9P8tprLZuDdMb4vXnSfP94:6ZVRMQNEB5Wq5zGskjSkcvPz', '2020-04-16 12:04:58', '2020-04-24 10:49:02', NULL);
INSERT INTO `user` VALUES (3, '傅', NULL, 'welong123', 'pbkdf2sha256:64000:18:24:n:s1tTAsN9sFx4mWPqhN8+3aVOQjsb/QaT:1tOCFW22lr3D4Ywyyp0CZdhi', '2020-04-18 11:40:43', '2020-04-26 19:36:45', '2020-04-26 19:36:45');
INSERT INTO `user` VALUES (4, '萧萧北风起', NULL, 'welong1', 'pbkdf2sha256:64000:18:24:n:m3Kn92oUe8G+u0vTE2X4u4/Bc77GAd+Y:ofmKQPqaXCkCyel6dlVNdfxs', '2020-04-18 17:14:50', '2020-05-06 21:24:49', NULL);
INSERT INTO `user` VALUES (5, '付', NULL, 'fusion', 'pbkdf2sha256:64000:18:24:n:riIQw7jPx//RAiKj1qmAqKr274RIdIVW:Bxrq0nWD2iYsh31F3gpUuKvO', '2020-04-24 12:46:40', '2020-05-06 09:53:15', '2020-05-06 09:53:15');
INSERT INTO `user` VALUES (6, '胡八一', NULL, 'bayi', 'pbkdf2sha256:64000:18:24:n:sKm3E0VyWcCv2gdgvQlNMGu2htwSxB8U:n0GTuD3y/7WK14uKo2Q7Jnwb', '2020-04-24 13:25:51', '2020-04-26 19:33:07', '2020-04-26 19:33:07');
INSERT INTO `user` VALUES (7, '测试1', NULL, 'ceshi1', 'pbkdf2sha256:64000:18:24:n:pTPLa1QRhFAcs2WBQcMDKXQbTvDQxlLG:IjTXheXCM9pmU8xvzxqQhBnG', '2020-04-26 19:57:19', '2020-04-26 20:31:14', '2020-04-26 20:31:15');
INSERT INTO `user` VALUES (11, '测试2', NULL, 'ceshi2', 'pbkdf2sha256:64000:18:24:n:P0Eeat0SDwiKn4yCFwitAy2bdPeOvcny:/s29fez6ENpievckLKIZiFN7', '2020-04-26 20:06:47', '2020-04-26 20:16:47', '2020-04-26 20:16:48');
INSERT INTO `user` VALUES (12, '傅红雪', NULL, 'redSnow', 'pbkdf2sha256:64000:18:24:n:s/F1XncgTTgxWRTaF+SR8T3pCnXyggiP:Jr9o/ZgJDpljR30K7roK8oxx', '2020-04-26 20:31:51', '2020-05-06 20:55:11', NULL);

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `group_id` int(0) NOT NULL COMMENT '分组id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_group
-- ----------------------------
INSERT INTO `user_group` VALUES (1, 1, 1, '2020-04-16 12:05:12', '2020-04-25 10:26:37', NULL);
INSERT INTO `user_group` VALUES (2, 3, 1, '2020-04-18 17:03:07', '2020-04-26 19:36:45', '2020-04-26 19:36:45');
INSERT INTO `user_group` VALUES (3, 4, 2, '2020-04-18 17:15:11', '2020-04-20 12:49:35', NULL);
INSERT INTO `user_group` VALUES (4, 5, 3, '2020-04-24 12:46:40', '2020-05-06 09:53:15', '2020-05-06 09:53:16');
INSERT INTO `user_group` VALUES (5, 6, 5, '2020-04-24 13:25:51', '2020-04-26 20:11:29', '2020-04-26 20:11:30');
INSERT INTO `user_group` VALUES (6, 7, 1, '2020-04-26 19:57:19', '2020-04-26 20:31:14', '2020-04-26 20:31:15');
INSERT INTO `user_group` VALUES (7, 11, 7, '2020-04-26 20:06:47', '2020-04-26 20:16:47', '2020-04-26 20:16:48');
INSERT INTO `user_group` VALUES (8, 12, 9, '2020-04-26 20:31:51', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
