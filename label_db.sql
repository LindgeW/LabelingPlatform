/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.18-log : Database - db_label_platform
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_label_platform` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `db_label_platform`;

/*Table structure for table `tb_data_type` */

DROP TABLE IF EXISTS `tb_data_type`;

CREATE TABLE `tb_data_type` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(20) DEFAULT NULL,
  `url` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_data_type` */

insert  into `tb_data_type`(`id`,`typeName`,`url`) values (1,'文本分类','annotate.html'),(2,'实体识别','ner_annotate.html'),(3,'语义相似度','semantic_annotate.html');

/*Table structure for table `tb_instance` */

DROP TABLE IF EXISTS `tb_instance`;

CREATE TABLE `tb_instance` (
  `instanceId` bigint(11) NOT NULL AUTO_INCREMENT,
  `taskName` varchar(20) DEFAULT NULL,
  `item` text,
  `tagDefault` varchar(20) DEFAULT NULL COMMENT '初始标签(随机生成)',
  `tagExpert` varchar(20) DEFAULT NULL COMMENT '专家标签(精标)',
  `tagModel` varchar(50) DEFAULT NULL COMMENT '模型标签(预测的k-best结果)',
  `status` int(2) DEFAULT '0',
  `tagNum` int(3) DEFAULT '0',
  PRIMARY KEY (`instanceId`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_instance` */

insert  into `tb_instance`(`instanceId`,`taskName`,`item`,`tagDefault`,`tagExpert`,`tagModel`,`status`,`tagNum`) values (1,'电商评论','评判专家：','负面','正面','正面',1,2),(2,'电商评论','5、统计信息（查看所有人的进度、任务）','负面','负面','正面;中立',1,2),(3,'电商评论','2、修改个人信息','负面','负面','负面;正面;中立',1,2),(4,'电商评论','4、导出数据','正面','正面','负面',1,2),(5,'电商评论','1、对标注员出现分歧的标注结果做决断（专家不能评定自己标的数据）','负面','中立','中立;正面',1,2),(6,'电商评论','1、标注员权限设置（默认标注员）：设置某位标注员为专家','正面','负面','负面;正面;中立',1,3),(7,'电商评论','3、标注规则（如：设定标签值、几人标一条数据）','正面','中立','中立',1,3),(8,'电商评论','3、标注历史记录（个人进度统计、任务统计）','负面','负面','正面',1,3),(9,'电商评论','2、上传数据（格式问题）','正面','中立','负面;中立;正面',1,3),(10,'电商评论','后台管理员：','负面','正面','负面',1,2),(11,'实体识别','我 为 IT 狂 ！','ORG','','',1,12),(12,'实体识别','我 来自 上海 浦东 新区 ！','PER','','',1,11),(13,'实体识别','天津大学 是 一所 不错 的 大学','PER','','',1,15),(14,'实体识别','人生 苦 短 ， 你 需要 Go','ORG','','',1,15),(15,'实体识别','谢谢 ， 祝你 幸福 快乐 每一天 ！','LOC','','',1,15),(31,'车评标注','2、以预训练的词向量模型构建词表，对OOV的词初始化为0向量或随机值或已知向量的均值','pos','','',0,0),(32,'车评标注',',一般 吧 ， 老人 机嘛 ， 给 外公 买 的','pos','','',0,0),(33,'车评标注','dsasda,asdfasdf','neg','','',0,0),(34,'车评标注','天津大学 是 一所 不错 的 大学','neg','','',0,0),(35,'车评标注','Embedding层加载预训练词向量的方案：','neg','','',0,0),(36,'车评标注','我 来自 上海 浦东 新区 ！','neg','','',0,0),(37,'车评标注','谢谢 ， 祝你 幸福 快乐 每一天 ！','pos','','',0,0),(38,'车评标注','pos,比 别的 手机 瘦 ， 价格 也 高 ， 电池 一般 ， 以上','gen','','',0,0),(39,'车评标注','1、以训练语料构建词表，对OOV的词初始化为0向量或随机值或已知向量的均值','pos','','',0,0),(40,'车评标注','3、以训练语料（训练过程更新）和预训练的词向量模型（训练过程不更新）分别构建词表，求和，对OOV的词初始化为0向量或随机值或已知向量的均值','gen','','',0,0),(41,'车评标注','人生 苦 短 ， 你 需要 Go','gen','','',0,0),(42,'车评标注',',信号 一般 ， 颜色 还 可以 ， 按键 老人 看 得 不是 很 清楚 ， 还是 买 红色 的 好 。','gen','','',0,0),(43,'车评标注',',外观 耐看 ， 屏幕 超赞 ， 不足 有 两个 ， 相机 像素 低 ， 续航 一般 。','gen','','',0,0),(44,'车评标注','注：一般更新比不更新要好点','pos','','',0,0),(45,'车评标注','我 为 IT 狂 ！','pos','','',0,0),(46,'代码克隆','public Integer getTotalRows() {return totalRows;} $$ public void setTotalRows(Integer totalRows) {this.totalRows = totalRows;}','否','','',1,1),(47,'代码克隆','public Integer getPageSize() {return pageSize;} $$ public void setPageSize(Integer pageSize) {this.pageSize = pageSize;}','否','','',1,2),(48,'代码克隆','public static Pager of(Integer offset, Integer pageSize){return new Pager(offset, pageSize);} $$ public static Pager of(Integer offset, Integer pageSize, Integer totalRows){return new Pager(offset, pageSize, totalRows);}','否','','',1,3),(49,'代码克隆','public Integer getOffset() {return offset;} $$ public void setOffset(Integer offset) {this.offset = offset;}','是','','',1,2);

/*Table structure for table `tb_instance_user` */

DROP TABLE IF EXISTS `tb_instance_user`;

CREATE TABLE `tb_instance_user` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(15) DEFAULT NULL,
  `instanceId` bigint(10) DEFAULT NULL,
  `tag` varchar(100) DEFAULT NULL,
  `taskname` varchar(20) DEFAULT NULL,
  `tagTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `responseTime` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_instance_user` */

insert  into `tb_instance_user`(`id`,`username`,`instanceId`,`tag`,`taskname`,`tagTime`,`responseTime`) values (1,'zsf123',1,'中立','电商评论','2019-08-29 18:28:20',1.484),(2,'zsf123',2,'负面','电商评论','2019-08-25 09:38:37',1.119),(3,'zsf123',3,'中立','电商评论','2019-09-01 15:15:44',1.151),(4,'zsf123',4,'中立','电商评论','2019-08-29 18:27:36',1.463),(5,'zsf123',5,'负面','电商评论','2019-08-29 18:24:09',1.959),(6,'zsn',1,'中立','电商评论','2019-08-30 20:39:14',2.525),(7,'zsn',2,'中立','电商评论','2019-08-25 09:56:05',1.511),(8,'zsn',3,'正面','电商评论','2019-08-25 09:56:20',15.224),(9,'zsn',4,'负面','电商评论','2019-08-25 09:56:22',1.951),(10,'zsn',5,'负面','电商评论','2019-09-01 08:50:14',1.872),(11,'zsn',6,'正面','电商评论','2019-08-25 09:56:49',25.344),(12,'zsn',7,'中立','电商评论','2019-08-25 09:56:51',1.32),(13,'zsn',8,'负面','电商评论','2019-08-25 09:56:52',1.16),(14,'zsn',9,'中立','电商评论','2019-08-25 09:56:53',1.015),(15,'zsn',10,'负面','电商评论','2019-08-25 09:56:54',0.887),(51,'zsf123',6,'负面','电商评论','2019-08-29 10:08:43',3.039),(52,'zsf123',7,'中立','电商评论','2019-08-29 10:30:29',1.988),(53,'zsf123',8,'负面','电商评论','2019-08-29 10:30:31',2.087),(56,'zsf123',9,'中立','电商评论','2019-08-29 16:46:58',2.392),(57,'zsf123',10,'正面','电商评论','2019-08-29 16:47:00',1.735),(87,'wlz+',14,'0_1_LOC&5_6_PER','实体识别','2019-08-31 20:27:38',10.765),(88,'wlz+',15,'0_1_PER&5_5_ORG','实体识别','2019-08-31 20:27:47',8.553),(89,'wlz+',13,'0_1_PER&3_4_LOC','实体识别','2019-08-31 20:33:01',12.604),(90,'wlz+',14,'0_1_ORG&4_5_PER&6_6_ORG','实体识别','2019-08-31 20:33:19',17.471),(91,'wlz+',15,'0_1_ORG&4_5_LOC&2_3_PER','实体识别','2019-08-31 21:02:17',7.247),(95,'ncu151',49,'是','代码克隆','2019-09-01 11:43:04',7.735),(96,'ncu151',47,'否','代码克隆','2019-09-01 11:45:19',7.3),(99,'ncu151',48,'否','代码克隆','2019-09-01 14:29:17',11.773);

/*Table structure for table `tb_task` */

DROP TABLE IF EXISTS `tb_task`;

CREATE TABLE `tb_task` (
  `taskname` varchar(20) NOT NULL,
  `datatype` smallint(6) DEFAULT '0',
  `corpussize` int(11) DEFAULT '0',
  `tags` varchar(100) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0' COMMENT '任务是否完成',
  `expertname` varchar(20) DEFAULT NULL COMMENT '任务发布者',
  PRIMARY KEY (`taskname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_task` */

insert  into `tb_task`(`taskname`,`datatype`,`corpussize`,`tags`,`status`,`expertname`) values ('代码克隆',3,4,'是;否',1,'zsf123'),('实体识别',2,5,'LOC;PER;ORG',1,'zsf123'),('电商评论',1,10,'正面;中立;负面',1,'zsf123'),('车评标注',1,15,'pos;neg;gen',0,'zsf123');

/*Table structure for table `tb_team` */

DROP TABLE IF EXISTS `tb_team`;

CREATE TABLE `tb_team` (
  `teamId` int(11) NOT NULL AUTO_INCREMENT,
  `teamName` varchar(20) NOT NULL,
  `taskName` varchar(20) NOT NULL,
  `members` varchar(50) DEFAULT '0',
  `status` tinyint(1) DEFAULT '0' COMMENT '团队是否有效',
  `expertname` varchar(20) DEFAULT NULL COMMENT '团队创建者',
  PRIMARY KEY (`teamId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_team` */

insert  into `tb_team`(`teamId`,`teamName`,`taskName`,`members`,`status`,`expertname`) values (1,'无敌小分队','电商评论','tomas;zsn;zsf123',1,'zsf123'),(2,'xxx','实体识别','wlz+',1,'zsf123'),(3,'adaf','代码克隆','ncu151',1,'zsf123');

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(15) NOT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'user',
  `teamName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_user` */

insert  into `tb_user`(`username`,`password`,`role`,`teamName`) values ('adam','333333','user',''),('ncu151','123456','user','adaf'),('tomas','112233','user','无敌小分队'),('wlz+','111222','user','xxx'),('zsf123','123123','admin','无敌小分队'),('zsn','123123','user','无敌小分队');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
