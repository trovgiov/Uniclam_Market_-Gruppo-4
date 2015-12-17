-- MySQL dump 10.13  Distrib 5.6.24, for osx10.8 (x86_64)
--
-- Host: sql4.freesqldatabase.com    Database: sql497212
-- ------------------------------------------------------
-- Server version	5.5.46-0ubuntu0.12.04.2

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
-- Table structure for table `carrello`
--

DROP TABLE IF EXISTS `carrello`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carrello` (
  `prodotto_barcode` varchar(200) NOT NULL,
  `spesa_idSpesa` int(11) NOT NULL,
  `quantita` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`prodotto_barcode`,`spesa_idSpesa`),
  KEY `fk_prodotto_has_spesa_spesa1_idx` (`spesa_idSpesa`),
  KEY `fk_prodotto_has_spesa_prodotto1_idx` (`prodotto_barcode`),
  CONSTRAINT `fk_prodotto_has_spesa_spesa1` FOREIGN KEY (`spesa_idSpesa`) REFERENCES `spesa` (`idSpesa`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_prodotto_has_spesa_prodotto1` FOREIGN KEY (`prodotto_barcode`) REFERENCES `prodotto` (`barcode`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrello`
--

LOCK TABLES `carrello` WRITE;
/*!40000 ALTER TABLE `carrello` DISABLE KEYS */;
INSERT INTO `carrello` VALUES ('1111',16,'21'),('1111',17,'1'),('1111',41,'32'),('1111',46,'1'),('1111',51,'12'),('1111',55,'1'),('1111',62,'1');
/*!40000 ALTER TABLE `carrello` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `pin` int(11) DEFAULT NULL,
  `scheda_idScheda` int(11) NOT NULL,
  PRIMARY KEY (`scheda_idScheda`),
  CONSTRAINT `fk_login_scheda1` FOREIGN KEY (`scheda_idScheda`) REFERENCES `scheda` (`idScheda`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (3566,45),(6009,57),(6350,76),(3873,77),(5582,78);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotto`
--

DROP TABLE IF EXISTS `prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prodotto` (
  `barcode` varchar(200) NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `costo` double DEFAULT NULL,
  `punti_prod` int(11) DEFAULT NULL,
  PRIMARY KEY (`barcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto`
--

LOCK TABLES `prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` VALUES ('1111','Tarallucci',2,1),('1112','Macine',3,2),('1113','Zucchero',4,4),('1114','Caffè',6,3);
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheda`
--

DROP TABLE IF EXISTS `scheda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheda` (
  `idScheda` int(11) NOT NULL AUTO_INCREMENT,
  `punti_totali` double DEFAULT NULL,
  `massimale_res` double DEFAULT NULL,
  `utente_email` varchar(45) NOT NULL,
  `data_attivazione` date DEFAULT NULL,
  PRIMARY KEY (`idScheda`,`utente_email`),
  KEY `fk_scheda_utente1_idx` (`utente_email`),
  CONSTRAINT `fk_scheda_utente1` FOREIGN KEY (`utente_email`) REFERENCES `utente` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheda`
--

LOCK TABLES `scheda` WRITE;
/*!40000 ALTER TABLE `scheda` DISABLE KEYS */;
INSERT INTO `scheda` VALUES (45,3566,11111,'guidolavespa@piaggio.it',NULL),(57,4,30,'dicostanzocristian@libero.it',NULL),(76,0,24324324342432,'thesilentman91@hotmail.it',NULL),(77,44,11047,'trovgiov@gmail.com',NULL),(78,0,234,'eneamarinelli@gmail.com',NULL);
/*!40000 ALTER TABLE `scheda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spesa`
--

DROP TABLE IF EXISTS `spesa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spesa` (
  `data_spesa` date DEFAULT NULL,
  `importo_tot` double DEFAULT NULL,
  `punti_spesa` double DEFAULT NULL,
  `idSpesa` int(11) NOT NULL,
  `scheda_idScheda` int(11) NOT NULL,
  `quantita` int(11) DEFAULT NULL,
  PRIMARY KEY (`idSpesa`,`scheda_idScheda`),
  KEY `fk_spesa_scheda1_idx` (`scheda_idScheda`),
  CONSTRAINT `fk_spesa_scheda1` FOREIGN KEY (`scheda_idScheda`) REFERENCES `scheda` (`idScheda`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spesa`
--

LOCK TABLES `spesa` WRITE;
/*!40000 ALTER TABLE `spesa` DISABLE KEYS */;
INSERT INTO `spesa` VALUES ('2015-12-17',NULL,NULL,16,45,NULL),('2015-12-17',NULL,NULL,17,45,NULL),('2015-12-17',NULL,NULL,24,45,NULL),('2015-12-17',NULL,NULL,41,77,NULL),('2015-12-17',NULL,NULL,46,45,NULL),('2015-12-17',NULL,NULL,51,77,NULL),('2015-12-16',NULL,NULL,55,45,NULL),('2015-12-17',NULL,NULL,62,45,NULL);
/*!40000 ALTER TABLE `spesa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utente` (
  `nome` varchar(45) DEFAULT NULL,
  `cognome` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `massimale_tot` double DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('Grazia','Di Costanzo','dicostanzocristian@libero.it','3283158333',30),('Enea','Marinelli','eneamarinelli@gmail.com','7676676',234),('Guido','La Vespa','guidolavespa@piaggio.it','63563653',3000),('Remo','La Barca','thesilentman91@hotmail.it','98998',24324324342432),('giovanni','trovini','trovgiov@gmail.com','1234',11111);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-17 16:35:06
