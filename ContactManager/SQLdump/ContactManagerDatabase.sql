-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: localhost    Database: Contact_Manager
-- ------------------------------------------------------
-- Server version	5.7.15

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
-- Table structure for table `Contact_Group`
--

DROP TABLE IF EXISTS `Contact_Group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Contact_Group` (
  `group_id` int(15) NOT NULL,
  `group_name` varchar(15) NOT NULL,
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `uc_group` (`group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Contact_Group`
--

LOCK TABLES `Contact_Group` WRITE;
/*!40000 ALTER TABLE `Contact_Group` DISABLE KEYS */;
INSERT INTO `Contact_Group` VALUES (2,'family_siblings'),(1,'friends'),(5,'friends_college'),(3,'neighbour_jack'),(4,'xyz');
/*!40000 ALTER TABLE `Contact_Group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Contact_Person`
--

DROP TABLE IF EXISTS `Contact_Person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Contact_Person` (
  `contact_Id` int(10) NOT NULL AUTO_INCREMENT,
  `Title` char(3) DEFAULT NULL,
  `First_Name` varchar(15) NOT NULL,
  `Middle_initial` varchar(1) DEFAULT NULL,
  `Last_Name` varchar(15) NOT NULL,
  `Sex` varchar(1) DEFAULT NULL,
  `Nickname` char(9) DEFAULT NULL,
  `Hobby` varchar(25) DEFAULT NULL,
  `Contact_person_Type` varchar(10) DEFAULT NULL,
  `Preffered_method_Type` varchar(10) DEFAULT NULL,
  `group_id` int(15) DEFAULT NULL,
  `Fax_Number` int(11) DEFAULT NULL,
  PRIMARY KEY (`contact_Id`),
  KEY `fk_ContactPerson_ContactGroup` (`group_id`),
  KEY `idx_Contact_Person_First_Name` (`First_Name`),
  CONSTRAINT `fk_ContactPerson_ContactGroup` FOREIGN KEY (`group_id`) REFERENCES `Contact_Group` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1112 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Contact_Person`
--

LOCK TABLES `Contact_Person` WRITE;
/*!40000 ALTER TABLE `Contact_Person` DISABLE KEYS */;
INSERT INTO `Contact_Person` VALUES (1077,NULL,'lopamudra','m','muduli','M',NULL,NULL,NULL,NULL,3,NULL),(1078,NULL,'atish','m','patra','M',NULL,NULL,NULL,NULL,3,NULL),(1109,NULL,'klkllkl','h','kskdjakdjaks','',NULL,NULL,NULL,NULL,4,NULL),(1110,NULL,'gggfgfgfdfdfd','','','',NULL,NULL,NULL,NULL,NULL,NULL),(1111,NULL,'gggfgfgfdfdfd','','','',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `Contact_Person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Phone_details`
--

DROP TABLE IF EXISTS `Phone_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Phone_details` (
  `phone_number` varchar(10) NOT NULL,
  `extension` varchar(5) DEFAULT NULL,
  `phone_type` varchar(10) DEFAULT NULL,
  `phone_status` varchar(10) DEFAULT NULL,
  `contact_Id` int(10) NOT NULL,
  PRIMARY KEY (`contact_Id`,`phone_number`),
  CONSTRAINT `fk_phone_ContactPerson` FOREIGN KEY (`contact_Id`) REFERENCES `Contact_Person` (`contact_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Phone_details`
--

LOCK TABLES `Phone_details` WRITE;
/*!40000 ALTER TABLE `Phone_details` DISABLE KEYS */;
INSERT INTO `Phone_details` VALUES ('9876543212',NULL,NULL,NULL,1077),('9876543210',NULL,NULL,NULL,1078),('',NULL,NULL,NULL,1109),('',NULL,NULL,NULL,1110),('',NULL,NULL,NULL,1111);
/*!40000 ALTER TABLE `Phone_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `address_id` int(10) NOT NULL AUTO_INCREMENT,
  `addressline1` varchar(50) DEFAULT NULL,
  `addressline2` varchar(25) DEFAULT NULL,
  `City` varchar(25) DEFAULT NULL,
  `State` varchar(25) DEFAULT NULL,
  `Pincode` varchar(15) DEFAULT NULL,
  `address_Type` varchar(10) DEFAULT NULL,
  `contact_Id` int(10) NOT NULL,
  PRIMARY KEY (`contact_Id`,`address_id`),
  UNIQUE KEY `address_id` (`address_id`),
  CONSTRAINT `fk_address_ContactPerson` FOREIGN KEY (`contact_Id`) REFERENCES `Contact_Person` (`contact_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (146,'broomfield',NULL,'dallas','texas','87767',NULL,1077),(147,'broomfield',NULL,'dallas','texas','75080',NULL,1078),(178,'jdhshjdsd',NULL,'','','',NULL,1109),(179,'',NULL,'','','87767',NULL,1110),(180,'broom',NULL,'','','',NULL,1111);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calender`
--

DROP TABLE IF EXISTS `calender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calender` (
  `date_of_birth` date DEFAULT NULL,
  `date_info` date DEFAULT NULL,
  `timezone` varchar(10) DEFAULT NULL,
  `meeting_request_status` varchar(10) DEFAULT NULL,
  `meeting_type` varchar(10) DEFAULT NULL,
  `history_info` varchar(50) DEFAULT NULL,
  `last_meeting_date` date DEFAULT NULL,
  `contact_Id` int(10) NOT NULL,
  PRIMARY KEY (`contact_Id`),
  KEY `idx_calender_date_of_birth` (`date_of_birth`),
  CONSTRAINT `fk_calender_ContactPerson` FOREIGN KEY (`contact_Id`) REFERENCES `Contact_Person` (`contact_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calender`
--

LOCK TABLES `calender` WRITE;
/*!40000 ALTER TABLE `calender` DISABLE KEYS */;
INSERT INTO `calender` VALUES ('2015-01-06',NULL,NULL,NULL,NULL,NULL,'2015-01-01',1077),('2015-01-06',NULL,NULL,NULL,NULL,NULL,'2015-01-06',1078),('2016-01-11',NULL,NULL,NULL,NULL,NULL,'2016-01-18',1109),('2016-01-14',NULL,NULL,NULL,NULL,NULL,NULL,1110),('2016-01-14',NULL,NULL,NULL,NULL,NULL,NULL,1111);
/*!40000 ALTER TABLE `calender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city_lookup`
--

DROP TABLE IF EXISTS `city_lookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city_lookup` (
  `city_id` varchar(10) NOT NULL,
  `city_name` varchar(15) NOT NULL,
  `state_id` varchar(10) NOT NULL,
  PRIMARY KEY (`city_id`,`state_id`),
  KEY `fk_city_stateLookup` (`state_id`),
  CONSTRAINT `fk_city_stateLookup` FOREIGN KEY (`state_id`) REFERENCES `state_lookup` (`state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city_lookup`
--

LOCK TABLES `city_lookup` WRITE;
/*!40000 ALTER TABLE `city_lookup` DISABLE KEYS */;
INSERT INTO `city_lookup` VALUES ('AL','Albany','NY'),('BH','BHUBANESWAR','OD'),('DA','DALLAS','TX'),('DE','Denver','CO'),('JA','Jacksonville','FL'),('MA','Madison','WI'),('MU','BOMBAY','MUM'),('PH','Phoenix','AZ'),('SE','Seattle','WA');
/*!40000 ALTER TABLE `city_lookup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `company_id` varchar(25) NOT NULL,
  `company_title` varchar(20) DEFAULT NULL,
  `contact_Id` int(10) NOT NULL,
  PRIMARY KEY (`contact_Id`,`company_id`),
  UNIQUE KEY `uc_comapny` (`company_title`),
  CONSTRAINT `fk_company_ContactPerson` FOREIGN KEY (`contact_Id`) REFERENCES `Contact_Person` (`contact_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country_lookup`
--

DROP TABLE IF EXISTS `country_lookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country_lookup` (
  `country_id` varchar(10) NOT NULL,
  `country_name` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`country_id`),
  UNIQUE KEY `uc_country` (`country_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country_lookup`
--

LOCK TABLES `country_lookup` WRITE;
/*!40000 ALTER TABLE `country_lookup` DISABLE KEYS */;
INSERT INTO `country_lookup` VALUES ('IND','INDIA'),('US','United States of America');
/*!40000 ALTER TABLE `country_lookup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_address`
--

DROP TABLE IF EXISTS `email_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_address` (
  `email_id` varchar(320) NOT NULL,
  `email_type` varchar(10) DEFAULT NULL,
  `group_id` int(15) DEFAULT NULL,
  `contact_Id` int(10) NOT NULL,
  PRIMARY KEY (`contact_Id`,`email_id`),
  KEY `fk_email_contactgroup` (`group_id`),
  CONSTRAINT `fk_email_ContactPerson` FOREIGN KEY (`contact_Id`) REFERENCES `Contact_Person` (`contact_Id`),
  CONSTRAINT `fk_email_contactgroup` FOREIGN KEY (`group_id`) REFERENCES `Contact_Group` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_address`
--

LOCK TABLES `email_address` WRITE;
/*!40000 ALTER TABLE `email_address` DISABLE KEYS */;
INSERT INTO `email_address` VALUES ('lopa@gmail.com',NULL,3,1077),('lopa@gmail.com',NULL,3,1078),('gh@gmail.com',NULL,4,1109),('',NULL,NULL,1110),('',NULL,NULL,1111);
/*!40000 ALTER TABLE `email_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journal`
--

DROP TABLE IF EXISTS `journal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journal` (
  `journal_id` int(10) NOT NULL,
  `record_type` varchar(5) DEFAULT NULL,
  `journal_status` varchar(10) DEFAULT NULL,
  `contact_Id` int(10) NOT NULL,
  PRIMARY KEY (`contact_Id`,`journal_id`),
  CONSTRAINT `fk_journal_ContactPerson` FOREIGN KEY (`contact_Id`) REFERENCES `Contact_Person` (`contact_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journal`
--

LOCK TABLES `journal` WRITE;
/*!40000 ALTER TABLE `journal` DISABLE KEYS */;
/*!40000 ALTER TABLE `journal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pincode_lookup`
--

DROP TABLE IF EXISTS `pincode_lookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pincode_lookup` (
  `pincode_number` int(10) NOT NULL,
  `city_id` varchar(10) NOT NULL,
  PRIMARY KEY (`pincode_number`,`city_id`),
  KEY `fk_pincode_cityLookup` (`city_id`),
  CONSTRAINT `fk_pincode_cityLookup` FOREIGN KEY (`city_id`) REFERENCES `city_lookup` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pincode_lookup`
--

LOCK TABLES `pincode_lookup` WRITE;
/*!40000 ALTER TABLE `pincode_lookup` DISABLE KEYS */;
INSERT INTO `pincode_lookup` VALUES (65466,'AL'),(97957,'BH'),(75080,'DA'),(80020,'DE'),(27565,'JA'),(98112,'MA'),(19919,'MU'),(85001,'PH'),(97575,'SE');
/*!40000 ALTER TABLE `pincode_lookup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state_lookup`
--

DROP TABLE IF EXISTS `state_lookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state_lookup` (
  `state_id` varchar(10) NOT NULL,
  `state_name` varchar(15) NOT NULL,
  `country_id` varchar(10) NOT NULL,
  PRIMARY KEY (`state_id`,`country_id`),
  UNIQUE KEY `uc_state` (`state_name`),
  KEY `fk_state_countryLookup` (`country_id`),
  CONSTRAINT `fk_state_countryLookup` FOREIGN KEY (`country_id`) REFERENCES `country_lookup` (`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state_lookup`
--

LOCK TABLES `state_lookup` WRITE;
/*!40000 ALTER TABLE `state_lookup` DISABLE KEYS */;
INSERT INTO `state_lookup` VALUES ('AZ','ARIZONA','US'),('CO','COLORADO','US'),('DL','DELHI','IND'),('FL','FLORIDA','US'),('KO','KOLKATTA','IND'),('MUM','MUMBAI','IND'),('NY','NEW YORK','US'),('OD','ODISHA','IND'),('TX','TEXAS','US'),('WA','Washington','US'),('WI','Wisconsin','US');
/*!40000 ALTER TABLE `state_lookup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'Contact_Manager'
--
/*!50003 DROP PROCEDURE IF EXISTS `DeleteRecords` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteRecords`(IN contact int(15))
BEGIN
		delete from address where contact_id = contact;
		delete from phone_details where contact_id = contact;
		delete from email_address where contact_id = contact;
        delete from calender where contact_id = contact;
        delete from contact_person where contact_id = contact;
        
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetDetailsInfo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetDetailsInfo`()
BEGIN
Select C.First_Name,C.Middle_initial,C.Last_Name,C.Sex,DATE_FORMAT(D.date_of_birth, '%m/%d/%Y'),
	   A.addressline1,A.city,A.state,A.pincode,
	   P.Phone_number,E.email_id,DATE_FORMAT(D.last_meeting_date, '%m/%d/%Y'),C.group_id,C.contact_id
from Contact_Person as C left outer join email_address as E on C.contact_id = E.contact_id
	left outer join calender as D on C.contact_id = D.contact_id
    left outer join Phone_details as P on C.contact_id = P.contact_id
    left outer join address as A on C.contact_id = A.contact_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Insert_contact` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `Insert_contact`(IN fname varchar(15),IN minit varchar(1),IN lname varchar(15),IN sex varchar(1),IN DOB date,
    IN address1 varchar(50), IN city varchar(25),IN state varchar(25), IN pincode varchar(15),
    IN phone varchar(10),IN email varchar(320),IN lastmeetdate date,groupid int(15))
BEGIN
	DECLARE newcontactID INT;
	IF (groupid = 0) then
     SET groupid = null;
	end if;
	insert into contact_person(First_Name,Middle_initial,Last_Name,sex,group_id) values(fname,minit,lname,sex,groupid);
    set newcontactID = (select LAST_INSERT_ID());
    INSERT INTO address (addressline1, city, state, pincode, contact_Id) VALUES (address1,city,state,pincode,newcontactID);
	insert into phone_details(phone_number,contact_id) values(phone,newcontactID);
    insert into email_address(email_id,group_id,contact_id) values(email,groupid,newcontactID);
    insert into calender(date_of_birth,last_meeting_date,contact_id) values (DOB,lastmeetdate,newcontactID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `selected_ContactID` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `selected_ContactID`(IN fname varchar(15),IN lname varchar(15),IN phone varchar(10),IN email varchar(320),OUT contact int(10))
BEGIN
if(fname is not null) then
	SET contact = (SELECT contact_Id FROM contact_person a WHERE a.First_Name = fname and a.Last_Name = lname);
elseif(phone is not null)then
	SET contact = (select contact_id from Phone_details where Phone_number = phone);
elseif(phone is not null)then
	SET contact = (select contact_id from email_address where email_id = email);
end if;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `selected_row_details` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `selected_row_details`(IN fname varchar(15),IN lname varchar(15))
BEGIN
DECLARE newcontactID INT;
SET newcontactID = (SELECT contact_Id FROM contact_person a WHERE a.First_Name = fname and a.Last_Name = lname); 
Select C.First_Name,C.Middle_initial,C.Last_Name,A.addressline1,A.city_id,P.Phone_number,E.email_id,C.group_id 
from Contact_Person as C ,email_address as E,Phone_details as P ,address as A where (C.contact_Id = newcontactID)
and (E.contact_Id = newcontactID) and (P.contact_Id = newcontactID) and (A.contact_Id = newcontactID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateRecords` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRecords`(IN contact int(15),IN fname varchar(15),IN minit varchar(1),IN lname varchar(15),IN sex varchar(1),IN DOB date,
    IN address1 varchar(50), IN city varchar(25),IN state varchar(25), IN pincode varchar(15),
    IN phone varchar(10),IN email varchar(320),IN lastmeetdate date,groupid int(15))
BEGIN
	IF (groupid = 0) then
     SET groupid = null;
	end if;
	update contact_person set First_Name = fname,Last_Name = lname,Middle_initial = minit,sex=sex,group_id = groupid
		where contact_id = contact;
	update address set addressline1 = address1,city = city, state = state, pincode = pincode where contact_Id = contact;
	update phone_details set phone_number = phone where contact_Id = contact;
    update email_address set email_id = email,group_id = groupid where contact_Id = contact;
    update calender set date_of_birth = DOB , last_meeting_date = lastmeetdate where contact_Id = contact;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-28 23:16:11
