/*
SQLyog Ultimate v8.71 
MySQL - 5.5.28-29.2-log : Database - regist
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`regist` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `regist`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(30) DEFAULT NULL,
  `pwd` varchar(30) DEFAULT NULL,
  `idname` varchar(40) DEFAULT NULL,
  `idcard` varchar(30) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `channel` varchar(20) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '1' COMMENT '1，未注册 2，注册中 3，注册成功 4，注册失败5,等待验证码',
  `mail` varchar(50) DEFAULT NULL,
  `mail_pass` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;

/*Table structure for table `cardinfo` */

DROP TABLE IF EXISTS `cardinfo`;

CREATE TABLE `cardinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idname` varchar(30) DEFAULT NULL,
  `idcard` varchar(30) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '0' COMMENT '0未检查 1 检测中 2,可用 3，不可用 4，异常',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `NewIndex1` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `mail` */

DROP TABLE IF EXISTS `mail`;

CREATE TABLE `mail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mail` varchar(30) DEFAULT NULL,
  `mail_pass` varchar(30) DEFAULT NULL,
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '0,未使用 1，已使用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mail` (`mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `phone` */

DROP TABLE IF EXISTS `phone`;

CREATE TABLE `phone` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(30) DEFAULT NULL,
  `channel` varchar(20) DEFAULT NULL COMMENT '1,淘码',
  `status` tinyint(2) DEFAULT '1' COMMENT '1,未使用 2，已使用',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
