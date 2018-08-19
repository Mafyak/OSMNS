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
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history` (
  `yearEmployed` smallint(4) DEFAULT NULL COMMENT 'Дата приёма на работу',
  `yearTerminated` smallint(4) DEFAULT NULL COMMENT 'Дата увольнения',
  `rating1` tinyint(1) NOT NULL COMMENT 'Рейтинг от 0 до 10',
  `rating2` tinyint(1) NOT NULL,
  `rating3` tinyint(1) NOT NULL,
  `rating4` tinyint(1) NOT NULL,
  `rating5` tinyint(1) NOT NULL,
  `totalRating` double NOT NULL COMMENT 'Средний рейтинг основывается на среднем значении рейтингов.',
  `hireAgain` tinyint(4) DEFAULT NULL COMMENT 'Запрос наймёт ли данный HR данного сотрудника ещё раз.',
  `idCompany` int(11) NOT NULL,
  `idHR` int(11) NOT NULL,
  `idEmployee` int(11) NOT NULL,
  `confirmed` tinyint(2) DEFAULT '0',
  `ratingID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ratingID`),
  KEY `idCompany_idx` (`idCompany`),
  KEY `idHRhead_idx` (`idHR`),
  KEY `empIDFK` (`idEmployee`),
  CONSTRAINT `idCompany` FOREIGN KEY (`idCompany`) REFERENCES `company` (`idcompany`),
  CONSTRAINT `idEmployee` FOREIGN KEY (`idEmployee`) REFERENCES `employee` (`idemployee`),
  CONSTRAINT `idHRhead` FOREIGN KEY (`idHR`) REFERENCES `hrhead` (`idhrhead`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='Таблица для хранения отзывов и данных о каждом месте работы сотрудников. Таблица так же связана с таблицей сотрудников, компаний и HR.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES (1998,2007,7,8,8,7,9,7.8,1,2,8,1,1,2),(2003,2007,7,8,7,7,9,7.6,1,11,63,11,0,11),(2001,2007,8,8,7,7,9,7.8,1,23,63,13,2,21),(2007,2011,7,8,9,7,6,7.4,1,24,8,14,1,22),(2003,2009,8,6,5,7,8,6.8,0,1,66,17,1,25),(2001,2003,7,6,8,8,9,7.6,1,11,63,11,0,27),(2009,2013,1,3,3,9,9,5,0,51,68,12,0,31),(2013,2015,6,5,4,2,3,4,0,51,68,12,1,33),(2001,2003,7,8,7,9,9,8,1,54,69,2,0,43),(2001,2005,6,8,9,7,7,7.4,0,51,69,11,0,44),(2009,2011,8,7,9,9,3,7.2,1,51,60,12,0,45),(2009,2013,6,2,3,2,3,3.2,0,51,62,2,0,46),(2001,2003,2,3,2,3,1,2.2,0,51,62,15,1,47),(2001,2003,3,3,4,1,2,2.6,0,11,63,11,0,49),(2007,2007,1,2,1,2,1,1.4,0,63,64,2,0,50),(2001,2005,6,3,4,2,1,3.2,0,44,11,12,0,52),(2001,2005,6,8,9,9,8,8,1,62,11,36,1,57),(2001,2005,6,8,8,9,1,6.4,0,63,11,12,0,58),(2001,2003,11,7,7,3,1,5.8,1,63,11,40,0,59),(2001,2003,6,6,4,2,4,4.4,1,63,11,40,0,60),(2013,3021,6,8,7,7,7,7,1,63,11,40,0,61),(2001,2005,6,6,9,3,3,5.4,1,63,11,14,0,62),(2001,1998,7,8,9,7,7,7.6,1,64,72,42,1,63),(2009,2011,6,8,7,7,1,5.8,0,64,72,42,1,64);
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
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
