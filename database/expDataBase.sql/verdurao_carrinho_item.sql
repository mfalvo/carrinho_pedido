-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: verdurao
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `carrinho_item`
--

DROP TABLE IF EXISTS `carrinho_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrinho_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `preco` decimal(38,2) DEFAULT NULL,
  `quantidade` int NOT NULL,
  `selecionado` bit(1) NOT NULL,
  `idcarrinho_fk` bigint DEFAULT NULL,
  `idproduto_fk` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8vuqtuw9v05cxfkjjfh9n2ktg` (`idcarrinho_fk`),
  KEY `FKiegew419civ4kju5kdww8epth` (`idproduto_fk`),
  CONSTRAINT `FK8vuqtuw9v05cxfkjjfh9n2ktg` FOREIGN KEY (`idcarrinho_fk`) REFERENCES `carrinho` (`id`),
  CONSTRAINT `FKiegew419civ4kju5kdww8epth` FOREIGN KEY (`idproduto_fk`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrinho_item`
--

LOCK TABLES `carrinho_item` WRITE;
/*!40000 ALTER TABLE `carrinho_item` DISABLE KEYS */;
INSERT INTO `carrinho_item` VALUES (8,32.80,3,_binary '',2,1),(9,32.80,1,_binary '',3,1),(10,32.80,1,_binary '',3,1),(11,84.50,2,_binary '',2,4),(12,43.20,2,_binary '',2,3),(13,28.50,3,_binary '',2,2),(14,84.50,1,_binary '',2,4),(15,84.50,1,_binary '',2,4),(16,28.50,1,_binary '',2,2),(17,32.80,1,_binary '',2,1),(18,28.50,1,_binary '',2,2),(19,43.20,1,_binary '',2,3),(20,32.80,1,_binary '',2,1),(21,43.20,1,_binary '',2,3),(22,32.80,3,_binary '',5,1),(23,43.20,1,_binary '',5,3),(24,84.50,1,_binary '',5,4),(25,28.50,1,_binary '',5,2);
/*!40000 ALTER TABLE `carrinho_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-28 22:46:11
