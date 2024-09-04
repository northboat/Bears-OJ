/*
SQLyog Community v13.2.0 (64 bit)
MySQL - 8.0.31 : Database - oj
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`oj` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `oj`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `floor` int NOT NULL AUTO_INCREMENT,
  `question` int NOT NULL,
  `from` varchar(32) NOT NULL,
  `to` int DEFAULT NULL,
  `content` varchar(256) NOT NULL,
  PRIMARY KEY (`floor`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `comment` */

insert  into `comment`(`floor`,`question`,`from`,`to`,`content`) values 
(1,1,'canoe',0,'很简单啊'),
(2,1,'canoe',0,'没有难度'),
(3,1,'canoe',0,'易如反掌');

/*Table structure for table `detail` */

DROP TABLE IF EXISTS `detail`;

CREATE TABLE `detail` (
  `num` int NOT NULL,
  `desc` varchar(128) DEFAULT NULL,
  `memory_limit` int DEFAULT NULL,
  `time_limit` int DEFAULT NULL,
  `example_input` varchar(64) DEFAULT NULL,
  `example_output` varchar(64) DEFAULT NULL,
  `tips` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `detail` */

insert  into `detail`(`num`,`desc`,`memory_limit`,`time_limit`,`example_input`,`example_output`,`tips`) values 
(1,'简单的排序题，将数组从小到大排序，别用内置的排序函数啦',0,4,'2,3,4,1','1,2,3,4',NULL);

/*Table structure for table `info` */

DROP TABLE IF EXISTS `info`;

CREATE TABLE `info` (
  `name` varchar(24) NOT NULL,
  `account` varchar(32) NOT NULL,
  `finished` int NOT NULL DEFAULT '0',
  `simple_finished` int NOT NULL DEFAULT '0',
  `middle_finished` int NOT NULL DEFAULT '0',
  `hard_finished` int NOT NULL DEFAULT '0',
  `grade` varchar(8) DEFAULT NULL,
  `skillful_lang` varchar(12) DEFAULT NULL,
  `java_finished` int NOT NULL DEFAULT '0',
  `c_finished` int NOT NULL DEFAULT '0',
  `python_finished` int NOT NULL DEFAULT '0',
  `register_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`name`,`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `info` */

insert  into `info`(`name`,`account`,`finished`,`simple_finished`,`middle_finished`,`hard_finished`,`grade`,`skillful_lang`,`java_finished`,`c_finished`,`python_finished`,`register_time`) values 
('canoe','northboat@163.com',0,0,0,0,'大四','Java',0,0,0,'2024-05-04 20:05');

/*Table structure for table `painting` */

DROP TABLE IF EXISTS `painting`;

CREATE TABLE `painting` (
  `num` int NOT NULL AUTO_INCREMENT,
  `title` varchar(32) NOT NULL,
  `desc` varchar(64) NOT NULL,
  `from` varchar(32) NOT NULL,
  `content` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `painting` */

insert  into `painting`(`num`,`title`,`desc`,`from`,`content`) values 
(1,'一颗棋子','测试','canoe','<div style=\"width: 50px; height: 50px; border-radius: 50%; background-color: black;box-shadow: #fff 65px -15px 0 -5px,#fff 25px -25px, #fff 30px 10px,#fff 60px 15px 0 -10px,#fff 85px 5px 0 -5px;\">\n</div>\n'),
(2,'七色花瓣','纯css实现的七色花瓣','canoe','<body>\n<div class=\"geometric-flowers\">\n    <div class=\"petal\"></div>\n    <div class=\"petal\"></div>\n    <div class=\"petal\"></div>\n    <div class=\"petal\"></div>\n    <div class=\"petal\"></div>\n    <div class=\"petal\"></div>\n    <div class=\"petal\"></div>\n    <div class=\"petal\"></div>\n</div>\n\n<style>\n.geometric-flowers {\n  width: 300px;\n  height: 300px;\n  position: relative;\n  position: fixed;\n  top: 50%;\n  left: 50%;\n  transform: translate(-50%, -50%);\n}\nhtml {\n  height: 100%;\n}\n.petal {\n  width: 50%;\n  height: 50%;\n  background: linear-gradient(45deg, #ac08, #f8c8);\n  border-top-left-radius: 100%;\n  border-bottom-right-radius: 100%;\n  position: absolute;\n  left: 50%;\n  transform-origin: 0 100%;\n}\n.petal:nth-child(1) {\n  transform: rotate(45deg);\n  background: linear-gradient(45deg, #8bf8, #bf88);\n}\n.petal:nth-child(2) {\n  transform: rotate(90deg);\n  background: linear-gradient(45deg, #bf88, #f8b8);\n}\n.petal:nth-child(3) {\n  transform: rotate(135deg);\n  background: linear-gradient(45deg, #f8b8, #fb88);\n}\n.petal:nth-child(4) {\n  transform: rotate(180deg);\n  background: linear-gradient(45deg, #fb88, #8fb8);\n}\n.petal:nth-child(5) {\n  transform: rotate(225deg);\n  background: linear-gradient(45deg, #8fb8, #0ff8);\n}\n.petal:nth-child(6) {\n  transform: rotate(270deg);\n  background: linear-gradient(45deg, #0ff8, #a8f8);\n}\n.petal:nth-child(7) {\n  transform: rotate(315deg);\n  background: linear-gradient(45deg, #ff08, #8bf8);\n}\n</style>\n</body>');

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `num` int NOT NULL AUTO_INCREMENT,
  `title` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `level` char(1) NOT NULL DEFAULT '低',
  `example` int NOT NULL DEFAULT '0' COMMENT '样例个数',
  `name` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'canoe' COMMENT '出题者',
  `func` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `tag` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `question` */

insert  into `question`(`num`,`title`,`level`,`example`,`name`,`func`,`tag`) values 
(1,'排序数组','低',21,'canoe','reverseNum(List<int> nums)','Sort&Search');

/*Table structure for table `reply` */

DROP TABLE IF EXISTS `reply`;

CREATE TABLE `reply` (
  `floor` int NOT NULL AUTO_INCREMENT,
  `topic` int NOT NULL,
  `from` varchar(32) NOT NULL,
  `to` int DEFAULT NULL,
  `content` varchar(256) NOT NULL,
  PRIMARY KEY (`floor`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `reply` */

insert  into `reply`(`floor`,`topic`,`from`,`to`,`content`) values 
(1,2,'canoe',0,'测试回复'),
(2,2,'canoe',1,'测试回复回复'),
(3,2,'canoe',1,'测试回复回复回复'),
(4,2,'canoe',0,'我也要当楼主'),
(5,2,'canoe',0,'哈哈哈'),
(6,2,'canoe',0,'无意义的占格'),
(7,2,'canoe',1,'你在搞什么飞机'),
(8,2,'canoe',4,'这叫层主，老哥'),
(9,2,'canoe',4,'哇库瓦库'),
(10,2,'canoe',4,'无聊'),
(11,2,'canoe',4,'能不能开摆'),
(12,2,'canoe',4,'?'),
(13,2,'canoe',4,'甚至有表情好吧'),
(14,2,'canoe',4,'真无敌了吧'),
(15,2,'canoe',4,'真有异议吧'),
(16,2,'canoe',0,'犯大吴疆土者，盛必击而破之'),
(17,2,'canoe',4,'喵喵喵'),
(18,7,'canoe',0,'你好');

/*Table structure for table `result` */

DROP TABLE IF EXISTS `result`;

CREATE TABLE `result` (
  `account` varchar(32) NOT NULL,
  `num` int NOT NULL,
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `key1` varchar(64) NOT NULL,
  `val1` varchar(64) NOT NULL,
  `key2` varchar(64) NOT NULL,
  `val2` varchar(64) NOT NULL,
  `key3` varchar(64) NOT NULL,
  `val3` varchar(64) NOT NULL,
  `key4` varchar(64) NOT NULL,
  `val4` varchar(64) NOT NULL,
  `code` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`account`,`num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `result` */

insert  into `result`(`account`,`num`,`title`,`key1`,`val1`,`key2`,`val2`,`key3`,`val3`,`key4`,`val4`,`code`) values 
('northboat@163.com',1,'成功通过 OwO','通过样例:','21','平均用时:','1ms','内存使用:','1Mb','创建/销毁容器耗时:','14ms','class Solution{<br>&nbsp;&nbsp;&nbsp;&nbsp;public reverseNum(List nums){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Arrays.sort(nums);<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return nums;<br>&nbsp;&nbsp;&nbsp;&nbsp;}<br>}');

/*Table structure for table `topic` */

DROP TABLE IF EXISTS `topic`;

CREATE TABLE `topic` (
  `num` int NOT NULL AUTO_INCREMENT,
  `title` varchar(24) NOT NULL,
  `desc` varchar(64) NOT NULL,
  `content` varchar(256) DEFAULT NULL,
  `tag` varchar(12) DEFAULT NULL,
  `from` varchar(32) NOT NULL,
  `contact` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `topic` */

insert  into `topic`(`num`,`title`,`desc`,`content`,`tag`,`from`,`contact`) values 
(3,'推荐一部最近在读的书','密码工程-机械工业出版社','很好的书，适合已入门有一定基础的密码学学者阅读','Books','canoe','northboat@163.com'),
(4,'test','快给我两个BUG','？？？？？？？？？','Bug发掘','canoe','northboat@163.com'),
(5,'计算机设计大赛组队','国三、省二经历，随便混','快来水','Teams','canoe','northboat@163.com'),
(6,'如何评价你秦计科专业','好的没话说','杠杠的','划水闲聊','canoe','northboat@163.com'),
(7,'这是一个功能测试','功能测试','哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈','划水闲聊','canoe','northboat@163.com');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(24) NOT NULL,
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `root` int NOT NULL DEFAULT '0',
  `level` int DEFAULT '1',
  PRIMARY KEY (`account`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`account`,`name`,`password`,`root`,`level`) values 
('1543625674@qq.com','northboat','123456',0,1),
('northboat@163.com','canoe','123456',0,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
