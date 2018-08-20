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
-- Table structure for table `hrhead`
--

DROP TABLE IF EXISTS `hrhead`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hrhead` (
  `idHRhead` int(11) NOT NULL,
  `fName` varchar(20) NOT NULL COMMENT 'Имя',
  `mName` varchar(20) DEFAULT NULL COMMENT 'Отчество',
  `lName` varchar(20) NOT NULL COMMENT 'Фамилия',
  `idCompany` int(11) DEFAULT NULL COMMENT 'Компания в которой работает HR',
  `SSN` int(9) DEFAULT NULL,
  PRIMARY KEY (`idHRhead`),
  UNIQUE KEY `SSN_UNIQUE` (`SSN`),
  KEY `id_idx` (`idHRhead`),
  KEY `idCompany_idx` (`idCompany`),
  CONSTRAINT `idLogin` FOREIGN KEY (`idHRhead`) REFERENCES `login` (`idlogin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Содержит HR сотрудников работающих в компаниях. Каждый сотрудник может работать на разные компании.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hrhead`
--

LOCK TABLES `hrhead` WRITE;
/*!40000 ALTER TABLE `hrhead` DISABLE KEYS */;
INSERT INTO `hrhead` VALUES (4,'Frank','','Peterso',1,NULL),(6,'Anton','Semenovich','Melnikov',2,NULL),(7,'Thomas','','Anchor',21,NULL),(8,'Zack','','Bolton',5,NULL),(9,'Daniel','','Edger',6,NULL),(10,'John','Peter','Waynard',7,NULL),(11,'Sergo','V','Bobo',63,NULL),(51,'Andrei12','','Yarmalovich12',NULL,NULL),(53,'9ly89ly8','','ly89ly8',NULL,NULL),(54,'Mark','','Samson',NULL,NULL),(55,'arht','','aerg',NULL,NULL),(57,'Sergo','','Huba',NULL,NULL),(58,'serbthse','','tr',NULL,NULL),(59,'Nikola','','Molodec',24,NULL),(60,'Samuel','','Progley',NULL,NULL),(61,'Joe','','Araujo',51,NULL),(62,'Dennis','','Tilton',NULL,NULL),(63,'Nikola','','Molodec',11,NULL),(64,'New','','Guy',NULL,NULL),(65,'Andrei','','Yarmalovich',NULL,NULL),(66,'Test','','Tester',NULL,NULL),(67,'Susan','','Dudess',NULL,NULL),(68,'Frankie','','Samuelson',51,NULL),(69,'Jessica','','Wall',51,NULL),(70,'Sergo','','Huba',51,NULL),(71,'tata','','tutu',NULL,NULL),(72,'tata','','tutu',65,NULL);
/*!40000 ALTER TABLE `hrhead` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-19  5:20:41
