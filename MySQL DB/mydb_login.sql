-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `email` varchar(25) NOT NULL,
  `idLogin` int(11) NOT NULL AUTO_INCREMENT,
  `pass` varchar(40) NOT NULL,
  `role` tinyint(5) NOT NULL DEFAULT '1' COMMENT 'User role. TinyInt type is used for future scalability.\n0 for admin\n1 for HR',
  PRIMARY KEY (`idLogin`),
  UNIQUE KEY `id_UNIQUE` (`idLogin`),
  UNIQUE KEY `login_email_uindex` (`email`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='Данные для захода в систему';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES ('admin1@me.com',1,'MTIzKkAjKERAQChHISs=',0),('admin2@me.com',2,'MTIzKkAjKERAQChHISs=',0),('admin3@me.com',3,'MTIzKkAjKERAQChHISs=',0),('hr1@me.com',4,'MTIzKkAjKERAQChHISs=',1),('hr3@me.com',6,'MTIzKkAjKERAQChHISs=',1),('hr4@me.com',7,'MTIzKkAjKERAQChHISs=',1),('hr5@me.com',8,'MTIzKkAjKERAQChHISs=',1),('hr6@me.com',9,'MTIzKkAjKERAQChHISs=',1),('qwe',10,'MTIzKkAjKERAQChHISs=',1),('user',11,'MTIzKkAjKERAQChHISs=',1),('newHRTom12',51,'MTIzKkAjKERAQChHISs=',1),('yukuky89',53,'MTIzKkAjKERAQChHISs=',1),('newagent@gmail.com',54,'MTIzKkAjKERAQChHISs=',1),('awef',55,'MTIzKkAjKERAQChHISs=',1),('mafyak',57,'MTIzKkAjKERAQChHISs=',1),('afwease',58,'MTIzKkAjKERAQChHISs=',1),('me@me.com',59,'MTIzKkAjKERAQChHISs=',1),('me23@me.com',60,'MTIzKkAjKERAQChHISs=',1),('me132@me.com',61,'MTIzKkAjKERAQChHISs=',1),('me65@me.com',62,'MTIzKkAjKERAQChHISs=',1),('mee@me.com',63,'MTIzKkAjKERAQChHISs=',1),('meagain@me.com',64,'MTIzKkAjKERAQChHISs=',1),('metrippled@me.com',65,'MTIzKkAjKERAQChHISs=',1),('test',66,'MTIzKkAjKERAQChHISs=',1),('dude@dude.com',67,'MTIzUVdFYXNkKkAjKERAQChHISs=',1),('newHRTom13',68,'MTIzUVdFYXNkKkAjKERAQChHISs=',1),('newHRTom',69,'MTIzUVdFYXNkKkAjKERAQChHISs=',1),('huba.est@gmail.com',70,'NTpbTkJFfCQqQCMoREBAKEchKw==',1),('ujyh',71,'MTIzMTIzUXEqQCMoREBAKEchKw==',1),('qweqwe',72,'MTIzMTIzUXEqQCMoREBAKEchKw==',1);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-19  5:20:40
