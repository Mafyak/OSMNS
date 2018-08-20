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
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `idEmployee` int(11) NOT NULL AUTO_INCREMENT,
  `fName` varchar(45) NOT NULL COMMENT 'Имя',
  `lName` varchar(45) NOT NULL COMMENT 'Фамилия',
  `mName` varchar(45) DEFAULT NULL COMMENT 'Отвечество',
  `ssn` int(11) NOT NULL COMMENT 'Номер социального страхования или иной уникальный идентификационный номер документа обязательного при приёме на работу',
  PRIMARY KEY (`idEmployee`),
  UNIQUE KEY `id_UNIQUE` (`idEmployee`),
  UNIQUE KEY `SSN_UNIQUE` (`ssn`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='Сотрудники компаний';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Samuel','Hogan','M',111001111),(2,'George','Aspesi','T',111001112),(3,'Peter','Monteforte','',111001113),(4,'Anthony','Burgess','Freitas',111001114),(5,'Daniel','Arajo','N',111001115),(6,'Samuel','Zielinsky','T',111001116),(7,'Peter','Wall','P',111001117),(8,'Michael','Aspesi','N',111001118),(9,'George','Tilton','N',111001119),(10,'Kathryn','Woods','',111001120),(11,'Nikola','Molodec','',11223333),(12,'Justin','Blair','',938228172),(13,'Sam','Lowerback','',738229182),(14,'Robert','Baulsch','',1),(15,'Ivan','Nickerback','',11223336),(16,'Caren','Newton','',11223337),(17,'Caren','Newton','',11223338),(18,'qwe','qwe','',222113333),(19,'Brian','Coxdor','William',938271922),(20,'Brian','Sam','William',5234123),(21,'Brian','Maxwell','William',6123),(22,'Samuel','Paul','Frank',81929412),(23,'Сэрго','Губа','В',123123123),(25,'Ð¢ÐµÑÑ2','ÑÑÐ²','Ð¢ÐµÑÑ2',13123123),(26,'Ð¢ÐµÑÑ3','Ð¢ÐµÑÑ3','Ð¢ÐµÑÑ3',123124123),(27,'Ð¢ÐµÑÑ4','Ð¢ÐµÑÑ4','Ð¢ÐµÑÑ4',12314123),(28,'Ð¢ÐµÑÑ5','Ð¢ÐµÑÑ5','Ð¢ÐµÑÑ5',51231232),(29,'Ð¢ÐµÑÑ6','Ð¢ÐµÑÑ6','Ð¢ÐµÑÑ6',53242343),(30,'Те�?�?7','Ð¢ÐµÑÑ7','Ð¢ÐµÑÑ7',93178312),(31,'Ð¢ÐµÑÑ9','Ð¢ÐµÑÑ9','Ð¢ÐµÑÑ9',23171922),(32,'Ð¢ÐµÑÑ10','Ð¢ÐµÑÑ10','Ð¢ÐµÑÑ10',124234123),(33,'Тест12','Тест12','Тест12',933123423),(34,'Тест13','Тест13','Тест13',931171922),(35,'Ð¢ÐµÑÑ14','Ð¢ÐµÑÑ14','Ð¢ÐµÑÑ14',14217823),(36,'Brian','Coxdor','William',313131),(37,'Brian','Coxdew','Terter',445676954),(39,'Brian','terr','William',123123124),(40,'Вася','Коняра','Коко',987987987),(41,'Вася','576567','456',134321324),(42,'Тест7','Coxdor','William',999999999);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-19  5:20:39
