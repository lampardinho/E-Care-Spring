CREATE DATABASE  IF NOT EXISTS `ecare` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ecare`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ecare
-- ------------------------------------------------------
-- Server version	5.6.25

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
-- Table structure for table `available_options`
--

DROP TABLE IF EXISTS `available_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `available_options` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tariff_id` int(11) NOT NULL,
  `option_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `available_options_fk0` (`tariff_id`),
  KEY `available_options_fk1` (`option_id`),
  CONSTRAINT `available_options_fk0` FOREIGN KEY (`tariff_id`) REFERENCES `tariffs` (`tariff_id`),
  CONSTRAINT `available_options_fk1` FOREIGN KEY (`option_id`) REFERENCES `options` (`option_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `available_options`
--

LOCK TABLES `available_options` WRITE;
/*!40000 ALTER TABLE `available_options` DISABLE KEYS */;
INSERT INTO `available_options` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,2,1),(6,2,2),(7,2,5),(8,2,6),(9,3,3),(10,3,4),(11,3,7),(12,4,1);
/*!40000 ALTER TABLE `available_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract_locking`
--

DROP TABLE IF EXISTS `contract_locking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract_locking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_id` int(11) NOT NULL,
  `locker_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contract_locking_fk0` (`contract_id`),
  KEY `contract_locking_fk1` (`locker_id`),
  CONSTRAINT `contract_locking_fk0` FOREIGN KEY (`contract_id`) REFERENCES `contracts` (`contract_id`),
  CONSTRAINT `contract_locking_fk1` FOREIGN KEY (`locker_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract_locking`
--

LOCK TABLES `contract_locking` WRITE;
/*!40000 ALTER TABLE `contract_locking` DISABLE KEYS */;
INSERT INTO `contract_locking` VALUES (1,6,1),(2,5,3);
/*!40000 ALTER TABLE `contract_locking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contracts`
--

DROP TABLE IF EXISTS `contracts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contracts` (
  `contract_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `tariff_id` int(11) NOT NULL,
  `phone_number` int(11) NOT NULL,
  `ballance` int(11) NOT NULL,
  PRIMARY KEY (`contract_id`),
  KEY `contracts_fk0` (`user_id`),
  KEY `contracts_fk1` (`tariff_id`),
  CONSTRAINT `contracts_fk0` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `contracts_fk1` FOREIGN KEY (`tariff_id`) REFERENCES `tariffs` (`tariff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contracts`
--

LOCK TABLES `contracts` WRITE;
/*!40000 ALTER TABLE `contracts` DISABLE KEYS */;
INSERT INTO `contracts` VALUES (2,1,1,1179506,1000),(3,1,2,3966788,500),(4,2,3,6543456,760),(5,3,4,5345463,1500),(6,3,3,8756432,0);
/*!40000 ALTER TABLE `contracts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `option_locking`
--

DROP TABLE IF EXISTS `option_locking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `option_locking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `selected_option_id` int(11) NOT NULL,
  `locked_option_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `option_locking_fk0` (`selected_option_id`),
  KEY `option_locking_fk1` (`locked_option_id`),
  CONSTRAINT `option_locking_fk0` FOREIGN KEY (`selected_option_id`) REFERENCES `options` (`option_id`),
  CONSTRAINT `option_locking_fk1` FOREIGN KEY (`locked_option_id`) REFERENCES `options` (`option_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `option_locking`
--

LOCK TABLES `option_locking` WRITE;
/*!40000 ALTER TABLE `option_locking` DISABLE KEYS */;
INSERT INTO `option_locking` VALUES (1,1,2),(2,1,3),(3,1,4),(4,2,1),(5,2,3),(6,2,4),(7,3,1),(8,3,2),(9,3,4),(10,5,6),(11,5,7),(12,6,5);
/*!40000 ALTER TABLE `option_locking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `options`
--

DROP TABLE IF EXISTS `options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `options` (
  `option_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `connection_price` int(11) NOT NULL,
  `monthly_price` int(11) NOT NULL,
  PRIMARY KEY (`option_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` VALUES (1,'Bit',100,300),(2,'Super bit',0,500),(3,'Internet-maxi',250,700),(4,'Internet-vip',300,800),(5,'Sms-mini',0,150),(6,'Sms-standart',50,250),(7,'Sms-maxi',100,300);
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `selected_options`
--

DROP TABLE IF EXISTS `selected_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `selected_options` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `option_id` int(11) NOT NULL,
  `contract_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `selected_options_fk0` (`option_id`),
  KEY `selected_options_fk1` (`contract_id`),
  CONSTRAINT `selected_options_fk0` FOREIGN KEY (`option_id`) REFERENCES `options` (`option_id`),
  CONSTRAINT `selected_options_fk1` FOREIGN KEY (`contract_id`) REFERENCES `contracts` (`contract_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `selected_options`
--

LOCK TABLES `selected_options` WRITE;
/*!40000 ALTER TABLE `selected_options` DISABLE KEYS */;
INSERT INTO `selected_options` VALUES (1,1,2),(2,5,2),(3,2,3),(4,6,3),(5,5,4),(6,7,5);
/*!40000 ALTER TABLE `selected_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariffs`
--

DROP TABLE IF EXISTS `tariffs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tariffs` (
  `tariff_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`tariff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariffs`
--

LOCK TABLES `tariffs` WRITE;
/*!40000 ALTER TABLE `tariffs` DISABLE KEYS */;
INSERT INTO `tariffs` VALUES (1,'Super zero',100),(2,'Endless talk',50),(3,'Smart mini',200),(4,'Red energy',150);
/*!40000 ALTER TABLE `tariffs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `birth_date` date NOT NULL,
  `passport_data` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Nickolay','Prigodich','1994-12-18','123456','Spb','1lampard@mail.ru','qwerty',1),(2,'Maria','Syrkina','1994-05-09','567456','Spb','mashasyrkina@mail.ru','qwerty',0),(3,'Sergey','Sivulskiy','1994-01-01','465723','Spb','progk@gmail.com','qwerty',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-07-09  2:23:06
