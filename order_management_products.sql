-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: order_management
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `quantity` int NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_name_uindex` (`name`),
  CONSTRAINT `products_chk_1` CHECK ((`quantity` >= 0)),
  CONSTRAINT `products_chk_2` CHECK ((`price` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Black Dog Tuxedo With Tails',22,36),(2,'Wooflink Tweed Jacket Yellow',34,60),(3,'Furry Runner Coat Black',66,70.5),(4,'Sweet Santa Coat',0,48),(5,'Pink Sweet Dreams Thermal Pajamas',54,30),(6,'Ultra Soft Minky Monkey Pajamas',11,41),(8,'Monkey Pajama',8,41),(9,'Pj Polar Bear Blue',54,24),(10,'Christmas Elf Sweater',77,44),(11,'Fleece Vest Red',43,15),(12,'Tattooed Mom Sweater',22,39),(13,'Boyfriend Sweater',72,39.5),(14,'Girly Girl Sweater Dress',19,34),(15,'Moosey Hoodie Sweater',63,44),(16,'Black Polka Dot Sweater',55,44),(17,'Dragon Hoodie Sweater',25,40),(18,'Love U Belly Band',53,24),(19,'Male Denim Pants Diaper',22,34),(20,'Top Dog Tee',45,39),(21,'Rescued Belly Band',78,24),(22,'Best In Show Belly Band',44,24),(23,'Bow Tie Collar Black',2,27),(24,'Star Shirt Tie Collar',75,27),(25,'Dog Bowtie Black And Red Plaid',4,10),(26,'Princess Jumper',65,46),(27,'Bruce Bow Tie Collar',93,27),(28,'Adam Shirt Tie Collar',22,27.5),(29,'Dog Bowtie Moxie',12,10),(30,'Ethan Bow Tie Collar',43,24);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-25 15:04:12
