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
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `idCompany` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT 'Название компании',
  `niche` varchar(45) DEFAULT NULL COMMENT 'Занимаемая ниша компании',
  `location` varchar(45) DEFAULT NULL COMMENT 'Месторасположение',
  `headCount` int(11) DEFAULT NULL COMMENT 'Количество сотрудников',
  `offCompId` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCompany`),
  UNIQUE KEY `idCompany_UNIQUE` (`idCompany`),
  UNIQUE KEY `offCompId_UNIQUE` (`offCompId`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COMMENT='Данные о компании в которых работают сотрудники.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'Crabtree CPA','CPA, CPP','Boston, MA',700,101),(2,'E3 Financial Planning','Financial Planning','Newport, RI',50,102),(4,'Hepco Construction','Remodeling','Plymouth, MA',25,104),(5,'Plymouth YMCA','Gym','Plymouth, MA',150,105),(6,'McDonalds of Chatham','Fast Food','Chatham, MA',40,106),(7,'Tires on the Run','Tires service','Sandwich, MA',20,107),(11,'Hyannis Painting Inc','Painting Services','Hyannis, MA',10,111),(12,'Drone Dynamics Inc','','',0,112),(13,'Happy Nails','','',0,113),(14,'Advanced Building Inc','','',0,114),(15,'McDonalds of Yarmouth','','',0,115),(16,'McDonalds of Dennis','','',0,116),(21,'Dunkin Donuts','Fast Food','Boston, MA',30,121),(22,'Dunkin Donuts of Quincy','','',0,122),(23,'New Poles Inc','','',0,123),(24,'Google Inc','Web Search','San Francisco, CA',50000,124),(44,'HWS','Web Design','Hyannis, MA',20,123123123),(51,'Bank of America Inc','Web Design','San Francisco, CA',1,12412432),(54,'Boston Consulting Inc','RnD','Boston, MA',8300,831724238),(55,'Airbnb Inc','Rental Services','San Francisco, CA',8000,151243432),(59,'Bank of America Inc','Web Design','Hyannis, MA',1,3031202),(60,'Bank of America Inc','Banking services','Hyannis, MA',50000,31452231),(62,'123','Painting Services','Hyannis, MA',20,41412432),(63,'Amazon','Online Marketplace','Hyannis, MA',321,12545642),(64,'refg','Painting Services','dbghnhnb',23,12456784),(65,'HWS','Web Design','Hyannis, MA',50000,56453423);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
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
